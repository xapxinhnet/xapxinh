package net.xapxinh.center.server.api.data;

import net.xapxinh.center.server.config.AppConfig;

public class DataServiceApiImpl extends AbstractDataServiceApi {

	public DataServiceApiImpl() {
		super();
	}

	@Override
	public String getDataServiceUrl() {
		return AppConfig.getInstance().DATASERVICE_URL;
	}
}
