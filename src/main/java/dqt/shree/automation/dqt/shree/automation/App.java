package dqt.shree.automation.dqt.shree.automation;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "QA Sanity" );
        try {

			Post.pagination();
			Post.getAllErrorFieldsForGrp();
			Post.getTransErrors();
			Post.getTransErrorsCount();
			Post.getFilteredTransErrors();
          //Post.updateBulkCorrectWithFilterGrp();
			//Post.updateBulkIgnoreWithFilterGrp();
            Post.getInvoice();
            Post.postTransSubmitCorrection();
            Post.csvFile();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
