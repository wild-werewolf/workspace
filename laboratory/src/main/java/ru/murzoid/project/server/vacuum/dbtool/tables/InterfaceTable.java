package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.Map;

public interface InterfaceTable{
	Map<String, Serializable> getGridData();
}
