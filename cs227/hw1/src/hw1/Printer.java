package hw1;

/**
 * 
 * @author andre The class Printer is used to mimic certain functions of a
 *         Printer. The Printer class is extended by a constructor, named
 *         Printer, and 9 methods.
 */

public class Printer {
	/**
	 * sheets is an int value used to quantify the number of sheets available to be
	 * printed by the printer.
	 */
	private int sheets = 0;

	/**
	 * trayCapacity is an int value used to quantify the number of sheets available
	 * to be held by the printer's tray.
	 */
	private int trayCapacity;

	/**
	 * pageNum is an int value used to count the current page number of the current
	 * print job .
	 */
	private int pageNum = 0;

	/**
	 * docLength is an int value used to store the length of a user requested print
	 * job.
	 */
	private int docLength;

	/**
	 * printPageCount is an int value used to quantify the number of sheets that
	 * have been printed by the printer.
	 */
	private int printPageCount;

	/**
	 * sheetsInTray is an int value used to quantify the number of sheets in the
	 * paper tray.
	 */
	private int sheetsInTray = 0;

	/**
	 * trayStatus is a boolean value used to store whether the printer's tray is in
	 * (true) or out (false) of the printer.
	 */
	private boolean trayStatus = true;

	/**
	 * This constructor is used to make creating Printer objects possible. This
	 * constructor takes a user-requested trayCapacity and assigns it to the
	 * user-created Printer object.
	 * 
	 * @param trayCapacity is an int value which stores the trayCapacity of the
	 *                     specific Printer object.
	 */
	public Printer(int trayCapacity) {
		this.trayCapacity = trayCapacity;
	}

	/**
	 * This method is used to start a print job of length docLength. It also will
	 * clear any previous print jobs, meaning after declaring a print job, the next
	 * page printed will always equal 0, even if a print job has been declared
	 * before it and isn't finished.
	 * 
	 * @param documentPages is an int value which stores the user-requested length
	 *                      of the print job.
	 */
	public void startPrintJob(int documentPages) {
		docLength = documentPages;
		pageNum = 0;

	}

	/**
	 * This method is used to return the sheets available to the printer to print. A
	 * conditional statement is in place-if the trayStatus is true (printer's tray
	 * is in), the method returns the sheets in the tray. If the trayStatus is false
	 * (printer's tray is not in), the method returns the sheets available to the
	 * printer.
	 * 
	 * if the tray is in:
	 ** 
	 * @return sheetsInTray is an int value used to store the number of sheets in
	 *         the paper tray. if the tray is not in:
	 ** @return sheets is an int value used to store the number of sheets available
	 *         for the printer to use for printing.
	 */
	public int getSheetsAvailable() {
		if (trayStatus == true) {
			return sheetsInTray;
		} else {
			return sheets;
		}
	}

	/**
	 * This method is used to return the next page number of the document that will
	 * be printed.
	 * 
	 * @return pageNum is an int value which is the next page number that will be
	 *         printed.
	 **/
	public int getNextPage() {
		return pageNum;
	}

	/**
	 * This method is used to return the total number of pages printed by the
	 * printer since its inception.
	 * 
	 * @return printPageCount is an int value which is the sum of all pages printed
	 *         by the printer since its construction.
	 */
	public int getTotalPages() {
		return printPageCount;
	}

	/**
	 * This method is used to "print" a page. It first determines whether the
	 * printer's tray is in, and if there are sheets available to print. If both of
	 * these cases are true, the method then calculates the number of pages printed,
	 * which is stored as numPagesPrinted. The method then calculates the page
	 * number. Finally, the method increases the page number by the number of page
	 * printed, decreases the sheets by the number of sheets used to print, and sets
	 * the sheetsInTray value equal to the updated amount of sheets available to the
	 * printer.
	 */
	public void printPage() {
		if (trayStatus && sheets != 0) {
			// this code took quite a while to figure out, but it essentially is able to
			// determine whether 1 or 0 sheets has been printed out
			double sheetNumer = (Math.min(sheets + 1, sheets));
			double sheetDenom = sheets + 1;
			double resultant = (sheetNumer / sheetDenom) + 0.5;
			int numPagesPrinted = (Math.round((int) resultant));

			// this code also took quite a while, and is really only explainable via
			// tracing.
			// I essentially found a way to loop the page number back to 0 if the current
			// page was the same as the document's length, but to add 1 to the page number
			// if the document has not been fully printed yet.
			pageNum = (Math.min(pageNum + 1, docLength));
			pageNum = (pageNum % (docLength));

			// the first 2 statements are used to store data that is required by some of the
			// "get" methods. the last statement updates the sheets in the tray so that if
			// the next
			// call removes the tray, the number of sheets in the tray is still preserved.
			printPageCount += numPagesPrinted;
			sheets -= numPagesPrinted;
			sheetsInTray = sheets;
		}
	}

	/**
	 * This method is used to "remove" the tray of the printer, setting the
	 * trayStatus as false, storing the sheets in the tray under sheetsInTray, and
	 * setting the sheets available (variable int sheets) equal to 0.
	 */
	public void removeTray() {
		trayStatus = false;
		sheetsInTray = sheets;
		// sheets available to print = 0, as the tray needs to be in the printer to
		// print a page
		sheets = 0;
	}

	/**
	 * This method is used to "replace" the tray of the printer, setting the
	 * trayStatus as true, and making the number of sheets available to the printer
	 * equal to the number of sheets in the tray.
	 */
	public void replaceTray() {

		trayStatus = true;

	}

	/**
	 * This method is used to add a user-requested amount of paper from the paper
	 * tray, so long as that number is not larger than the capacity of the paper
	 * tray. If the requested amount plus the current amount adds to greater than
	 * the tray's capacity, the method will stop adding sheets once the sheets in
	 * the tray equals the capacity of the tray.
	 * 
	 * @param sheetsReqAdded is an int value which is the user requested number of
	 *                       sheets to be added from the paper tray.
	 */
	public void addPaper(int sheetsReqAdded) {
		sheets = sheetsInTray + (Math.min(this.trayCapacity - sheetsInTray, sheetsReqAdded));
		sheetsInTray = sheets;

	}

	/**
	 * This method is used to remove a user-requested amount of paper from the paper
	 * tray, so long as that number is not larger than the number of sheets
	 * currently in the paper tray. If the current amount minus the requested amount
	 * makes the sheets available less than 0, or negative, the method defaults to a
	 * sheets value of 0.
	 * 
	 * @param sheetsReqSubtracted is an int value which is the user requested number
	 *                            of sheets to be removed from the paper tray.
	 */
	public void removePaper(int sheetsReqSubtracted) {
		sheets = sheetsInTray - (Math.min(sheetsInTray, sheetsReqSubtracted));
		sheetsInTray = sheets;

	}
}