package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToEUR implements IConversion {

    // private static final String URL =
    // "https://apidatos.ree.es/es/datos/mercados/precios-mercados-tiempo-real?";
    private static final Double AVERAGE_FACTOR = 0.2533;

    // private Map<String, String> params;
    // private Map<String, String> headers;

    // private Double factor;

    /*
     * public ConversionToEUR() {
     * this.params = new HashMap<>();
     * LocalDateTime start = LocalDate.now().atStartOfDay();
     * LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
     * params.put("start_date", start.truncatedTo(ChronoUnit.MINUTES).toString());
     * params.put("end_date", end.truncatedTo(ChronoUnit.MINUTES).toString());
     * this.headers = new HashMap<>();
     * 
     * try {
     * String jsonString = HTTPRequest.getRequest(URL, params, headers);
     * } catch (Exception e) {
     * e.printStackTrace();
     * factor = AVERAGE_FACTOR;
     * }
     * }
     */

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * AVERAGE_FACTOR;
        else
            return 0.0;
    }
}