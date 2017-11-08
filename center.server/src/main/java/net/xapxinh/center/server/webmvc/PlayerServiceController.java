package net.xapxinh.center.server.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import net.xapxinh.center.server.entity.Player;
import net.xapxinh.center.server.exception.UnknownPlayerException;
import net.xapxinh.center.server.persistence.service.IPlayerPersistenceService;

@RestController
@EnableWebMvc
public class PlayerServiceController extends AbstractPlayerServiceController {

	@Autowired
	private IPlayerPersistenceService playerPersistenceService;
	
	@Override
	protected Player getPlayer(Long playerId) throws UnknownPlayerException {
		if (playerId != null) {
			Player player = playerPersistenceService.findById(playerId);
			if (player != null) {
				return player;
			}
		}
		throw new UnknownPlayerException(playerId + "");
	}

}
