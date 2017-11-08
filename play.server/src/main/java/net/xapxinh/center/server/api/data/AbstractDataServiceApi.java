package net.xapxinh.center.server.api.data;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import net.xapxinh.center.server.entity.Player;
import net.xapxinh.center.server.exception.DataServiceException;
import net.xapxinh.center.shared.dto.Album;
import net.xapxinh.center.shared.dto.Song;
import net.xapxinh.center.shared.dto.YoutubeVideos;

public abstract class AbstractDataServiceApi implements DataServiceApi {

	private final RestTemplate restClient;

	public AbstractDataServiceApi() {
		restClient = new RestTemplate();
		restClient.setErrorHandler(new DataServiceExceptionHandler());
		final ClientHttpRequestInterceptor acceptHeaderJson
				= new AcceptHeaderHttpRequestInterceptor(MediaType.APPLICATION_JSON);
		restClient.setInterceptors(Arrays.asList(acceptHeaderJson));
	}

	class AcceptHeaderHttpRequestInterceptor implements ClientHttpRequestInterceptor {
		  private final MediaType headerValue;
		  public AcceptHeaderHttpRequestInterceptor(MediaType headerValue) {
		    this.headerValue = headerValue;
		  }
		  @Override
		  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			    final HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
			    requestWrapper.getHeaders().setAccept(Arrays.asList(headerValue));
			    return execution.execute(requestWrapper, body);
		  }
	}

	@Override
	public List<Album> searchAlbums(Player player, String searchKey, String searchScope, int pageNumber, int pageSize) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/albums/search")
			    .queryParam("mac", player.getMac())
			    .queryParam("searchKey", searchKey)
			    .queryParam("searchScope", searchScope)
			    .queryParam("pageNumber", String.valueOf(pageNumber))
			    .queryParam("pageSize", String.valueOf(pageSize))
			    .build()
			    .toUri();
		try {
			return restClient.getForObject(targetUrl, AlbumArrayList.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}

	@Override
	public void insertPlayer(Player player) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/players")
			    .build()
			    .toUri();
		try {
			restClient.put(targetUrl, player.getMac());
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}

	@Override
	public Album getAlbum(Player player, Long albumId) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/albums/" + albumId) // /albums/{albumId}
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();
		return restClient.getForObject(targetUrl.toString(), Album.class);
	}

	@Override
	public List<Album> getSpecialAlbums(Player player) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/albums/special")
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();

		try {
			return restClient.getForObject(targetUrl, AlbumArrayList.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}

	private DataServiceException handleException(Exception e) {
		if (e instanceof UnknownPlayerException) {
			return (UnknownPlayerException) e;
		}
		if (e.getCause() instanceof DataServiceException) {
			return (DataServiceException) e.getCause();
		}
		return new DataServiceException(e.getMessage());
	}

	@Override
	public void increaseAlbumListenCount(Player player, Long albumId) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/albums/" + albumId) // /albums/{albumId}
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();
		try {
			restClient.postForObject(targetUrl.toString(), albumId, DataServiceMesage.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}
	

	@Override
	public YoutubeVideos searchYoutubeVideos(Player player, String key, String pageToken) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/videos/search")
			    .queryParam("mac", player.getMac())
			    .queryParam("key", key)
			    .queryParam("pageToken", pageToken)
			    .build()
			    .toUri();

		try {
			return restClient.getForObject(targetUrl, YoutubeVideos.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}
	
	
	@Override
	public String getYoutubeVideoUrl(Player player, String videoId) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/videos/" + videoId + "/videourl")
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();

		try {
			return restClient.getForObject(targetUrl, String.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}
	
	@Override
	public String getYoutubeAudioUrl(Player player, String videoId) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
				.path("/videos/" + videoId + "/audiourl")
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();

		try {
			return restClient.getForObject(targetUrl, String.class);
		}
		catch (final Exception e) {
			throw handleException(e);
		}
	}
	

	@Override
	public Song getSong(Player player, Long songId) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(getDataServiceUrl())
			    .path("/songs/" + songId) // /songs/{albumId}
			    .queryParam("mac", player.getMac())
			    .build()
			    .toUri();
		return restClient.getForObject(targetUrl.toString(), Song.class);
	}
}
