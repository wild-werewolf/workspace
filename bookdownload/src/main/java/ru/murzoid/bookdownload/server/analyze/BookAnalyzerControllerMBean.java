package ru.murzoid.bookdownload.server.analyze;

import javax.ejb.Local;

import ru.murzoid.bookdownload.server.BasicService;

@Local
public interface BookAnalyzerControllerMBean extends BasicService
{
	void executeAnalyze();
}