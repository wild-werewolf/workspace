package ru.murzoid.controlled.bipedwalking.layout;

//VerticalLayout.java
//������� �������� ������������, ����������� ���������� � ������������ ���
import java.awt.*;
import javax.swing.*;


public class VerticalLayout implements LayoutManager
{
	// ������ ����������� ���������� � ����������
	public void layoutContainer(Container c)
	{
		Component comps[] = c.getComponents();
		int currentY = 5;
		for (int i = 0; i < comps.length; i++)
		{
			// ���������������� ������ ����������
			Dimension pref = comps[i].getPreferredSize();
			// ��������� ��������� ���������� �� ������
			comps[i].setBounds(5, currentY, pref.width, pref.height);
			// ���������� � 5 ��������
			currentY += 3;
			currentY += pref.height;
		}
	}

	// ��� ��� ������ ��� �� �����������
	public void addLayoutComponent(String name, Component comp)
	{}

	public void removeLayoutComponent(Component comp)
	{}

	// ����������� ������ ��� ����������
	public Dimension minimumLayoutSize(Container c)
	{
		return calculateBestSize(c);
	}

	// ���������������� ������ ��� ����������
	public Dimension preferredLayoutSize(Container c)
	{
		return calculateBestSize(c);
	}

	private Dimension size = new Dimension();

	// ��������� ����������� ������ ����������
	private Dimension calculateBestSize(Container c)
	{
		// ������� �������� ����� ����������
		Component[] comps = c.getComponents();
		int maxWidth = 0;
		for (int i = 0; i < comps.length; i++)
		{
			int width = comps[i].getWidth();
			// ����� ���������� � ������������ ������
			if (width > maxWidth) maxWidth = width;
		}
		// ����� ���������� � ������ ������ �������
		size.width = maxWidth + 5;
		// ��������� ������ ����������
		int height = 0;
		for (int i = 0; i < comps.length; i++)
		{
			height += 5;
			height += comps[i].getHeight();
		}
		size.height = height;
		return size;
	}

}
