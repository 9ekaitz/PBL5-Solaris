package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import eus.solaris.solaris.service.multithreading.modes.Kind;

public class FormatterJSON {
    private Kind kind;
    private String label;

    Map<Instant, Double> data;

    public FormatterJSON(Map<Instant, Double> data) {
        this.kind = Kind.LINE;
        this.label = "Data";
        this.data = data;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private List<String> getLabels(Map<Instant, Double> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.forLanguageTag("ESP"))
                .withZone(ZoneId.of("Europe/Madrid"));
        List<String> labels = new ArrayList<>();
        for (Instant instant : data.keySet()) {
            labels.add(formatter.format(instant));
        }
        return labels;
    }

    private List<Double> getValues(Map<Instant, Double> data) {
        List<Double> values = new ArrayList<>();
        for (Double value : data.values()) {
            values.add(value);
        }
        return values;
    }

    private JSONArray getDatasets(Map<Instant, Double> data) {
        JSONArray datasets = new JSONArray();
        JSONObject dataset = new JSONObject();
        dataset.put("label", label);
        dataset.put("data", new JSONArray(getValues(data)));
        dataset.put("backgroundColor", "rgba(255, 99, 132, 0.7)");
        datasets.put(dataset);
        return datasets;
    }

    private JSONObject getData(Map<Instant, Double> data) {
        JSONObject innerData = new JSONObject();
        innerData.put("labels", new JSONArray(getLabels(data)));
        innerData.put("datasets", getDatasets(data));
        return innerData;
    }

    public JSONObject getJSON() {
        JSONObject output = new JSONObject();
        output.put("type", this.kind.toString().toLowerCase());
        output.put("data", getData(this.data));
        return output;
    }

}
