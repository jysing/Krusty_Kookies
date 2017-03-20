package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class ProductionPane extends BasicPane {

	public ProductionPane() {
		super(new Database());
	}
}
