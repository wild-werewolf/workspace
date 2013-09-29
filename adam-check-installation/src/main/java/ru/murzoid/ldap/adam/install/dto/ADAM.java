package ru.murzoid.ldap.adam.install.dto;

public class ADAM
{
	private static final String HOST="localhost";
	private static final String ADAMAdmin="CN=ADAMAdmin,OU=users";
	private String name="WQAInstance";
	private String install="";
	private String uninstal="";
	private String port="389";
	private String sslPort="636";
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getInstall()
	{
		return install;
	}
	public void setInstall(String install)
	{
		this.install = install;
	}
	public String getUninstal()
	{
		return uninstal;
	}
	public void setUninstal(String uninstal)
	{
		this.uninstal = uninstal;
	}
	public String getPort()
	{
		return port;
	}
	public void setPort(String port)
	{
		this.port = port;
	}
	public String getSslPort()
	{
		return sslPort;
	}
	public void setSslPort(String sslPort)
	{
		this.sslPort = sslPort;
	}
	public static String getHost()
	{
		return HOST;
	}
	public static String getAdamadmin()
	{
		return ADAMAdmin;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((install == null) ? 0 : install.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((sslPort == null) ? 0 : sslPort.hashCode());
		result = prime * result
				+ ((uninstal == null) ? 0 : uninstal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ADAM other = (ADAM) obj;
		if (install == null)
		{
			if (other.install != null) return false;
		}
		else if (!install.equals(other.install)) return false;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		if (port == null)
		{
			if (other.port != null) return false;
		}
		else if (!port.equals(other.port)) return false;
		if (sslPort == null)
		{
			if (other.sslPort != null) return false;
		}
		else if (!sslPort.equals(other.sslPort)) return false;
		if (uninstal == null)
		{
			if (other.uninstal != null) return false;
		}
		else if (!uninstal.equals(other.uninstal)) return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "ADAM [install=" + install + ", name=" + name + ", port=" + port
				+ ", sslPort=" + sslPort + ", uninstal=" + uninstal + "]";
	}
}
