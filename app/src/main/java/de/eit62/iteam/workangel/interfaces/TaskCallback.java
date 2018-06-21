package de.eit62.iteam.workangel.interfaces;


/**
 * This interface should be implemented by {@code Activities} that want to call a web service method through
 * a retained {@code Fragment's} {@link android.os.AsyncTask}.
 * The methods to implement will be called in the corresponding methods in the Task.
 *
 * @param <Progress> the type of progress indicator
 * @param <Result>   the type of result of the web service call
 * @author Lukas
 */
public interface TaskCallback<Progress, Result> {
	
	void onPreExecute();
	
	void onProgressUpdate(Progress... progress);
	
	void onCancelled();
	
	void onPostExecute(Result result);
}
