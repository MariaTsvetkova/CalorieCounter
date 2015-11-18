package com.example.weightcontroller.fragments;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.weightcontroller.MainCaloriesController;
import com.example.weightcontroller.R;
import com.example.weightcontroller.additionstaticclasses.DateUtils;
import com.example.weightcontroller.database.Calories;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class GraphicalFragment extends Fragment {
	private User user;
	private SampleDBAdapter handler;
	private List<Calories> caloriesList;
	private View view;
	private List<Date> dateList = new ArrayList<Date>();
	private List<Double> calorieBurn = new ArrayList<Double>();
	private GraphicalView mChart;
	public static final int TEXT_SIZE_XHDPI = 24;
	public static final int TEXT_SIZE_HDPI = 20;
	public static final int TEXT_SIZE_MDPI = 18;
	public static final int TEXT_SIZE_LDPI = 13;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.graph_fragment, container, false);
		user = ((MainCaloriesController) getActivity()).getUser();
		handler = new SampleDBAdapter(view.getContext());
		caloriesList = handler.readAllActivities(user.getId());

		Collections.sort(caloriesList, new Comparator<Calories>() {

			@Override
			public int compare(Calories c1, Calories c2) {
				if (DateUtils.getDate(c1.getDate()).before(
						DateUtils.getDate(c2.getDate())))
					return -1;
				if (c1.getDate() == c2.getDate())
					return 0;
				return 1;
			}

		});

		if (caloriesList != null && caloriesList.size() > 0) {
			String date = caloriesList.get(0).getDate();
			dateList.add(DateUtils.getDate(date));
			for (Calories c : caloriesList) {
				if (c.getDate().equals(date)) {
					continue;
				}
				date = c.getDate();
				dateList.add(DateUtils.getDate(date));

			}

			for (Date d : dateList) {
				Double sum = 0.0;
				for (Calories c : caloriesList) {
					if (DateUtils.getDateString(d).equals(c.getDate())) {
						sum += Double.valueOf(c.getCalorieValue());
					}

				}
				calorieBurn.add(sum);
			}
		}
		openChart();
		return view;
	}

	private void openChart() {
		TimeSeries series = new TimeSeries("Calorie");

		for (int i = 0; i < calorieBurn.size(); i++) {
			series.add(dateList.get(i), calorieBurn.get(i));
		}
		// Creating TimeSeries
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer renderer = new XYSeriesRenderer();

		renderer.setColor(Color.YELLOW);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setLineWidth(2);
		renderer.setDisplayChartValues(true);

		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setPointSize(4.0f);
		switch (getResources().getDisplayMetrics().densityDpi) {
		case DisplayMetrics.DENSITY_XHIGH:
			mRenderer.setMargins(new int[] { 40, 90, 25, 10 });
			mRenderer.setAxisTitleTextSize(TEXT_SIZE_XHDPI);
			mRenderer.setChartTitleTextSize(TEXT_SIZE_XHDPI);
			mRenderer.setLabelsTextSize(TEXT_SIZE_XHDPI);
			mRenderer.setLegendTextSize(TEXT_SIZE_XHDPI);
			renderer.setChartValuesTextSize(TEXT_SIZE_XHDPI);
			break;
		case DisplayMetrics.DENSITY_HIGH:
			mRenderer.setMargins(new int[] { 30, 50, 20, 10 });
			mRenderer.setAxisTitleTextSize(TEXT_SIZE_HDPI);
			mRenderer.setChartTitleTextSize(TEXT_SIZE_HDPI);
			mRenderer.setLabelsTextSize(TEXT_SIZE_HDPI);
			mRenderer.setLegendTextSize(TEXT_SIZE_HDPI);
			renderer.setChartValuesTextSize(TEXT_SIZE_HDPI);
			break;
		default:
			mRenderer.setMargins(new int[] { 30, 50, 20, 10 });
			mRenderer.setAxisTitleTextSize(TEXT_SIZE_LDPI);
			mRenderer.setChartTitleTextSize(TEXT_SIZE_LDPI);
			mRenderer.setLabelsTextSize(TEXT_SIZE_LDPI);
			mRenderer.setLegendTextSize(TEXT_SIZE_LDPI);
			renderer.setChartValuesTextSize(TEXT_SIZE_LDPI);
			break;
		}

		LinearLayout chartContainer = (LinearLayout) view
				.findViewById(R.id.chart_container);
		// Creating a Time Chart
		mChart = (GraphicalView) ChartFactory.getTimeChartView(
				view.getContext(), dataset, mRenderer, "dd-MMM-yyyy");
		mRenderer.setClickEnabled(true);
		mRenderer.setSelectableBuffer(10);
		mChart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Format formatter = new SimpleDateFormat("dd-MMM-yyyy");

				SeriesSelection seriesSelection = mChart
						.getCurrentSeriesAndPoint();

				if (seriesSelection != null) {
					int seriesIndex = seriesSelection.getSeriesIndex();
					String selectedSeries = "Calories";

					long clickedDateSeconds = (long) seriesSelection
							.getXValue();
					Date clickedDate = new Date(clickedDateSeconds);
					String strDate = formatter.format(clickedDate);

					int amount = (int) seriesSelection.getValue();

					Toast.makeText(view.getContext(),
							selectedSeries + " on " + strDate + " : " + amount,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		chartContainer.addView(mChart);

	}

}
