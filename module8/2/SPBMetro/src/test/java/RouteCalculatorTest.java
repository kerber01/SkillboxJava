import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;


public class RouteCalculatorTest extends TestCase {

  List<Station> redStations;
  List<Station> blueStations;
  List<Station> whiteStations;
  RouteCalculator routeCalculator;
  ArrayList<Station> expectedOnTheLineRoute;
  ArrayList<Station> expectedOneConnectionRoute;
  ArrayList<Station> expectedTwoConnectionsRoute;
  StationIndex stationIndex;

  @Before
  @Override
  protected void setUp() throws Exception {

    Line redLine = new Line(1, "red");

    redLine.addStation(new Station("Уручье", redLine));
    redLine.addStation(new Station("Борисовский тракт", redLine));
    redLine.addStation(new Station("Восток", redLine));
    redLine.addStation(
        new Station("Октябрьская", redLine)); //connects to blue line's "Купаловская" station

    redStations = redLine.getStations();

    //-----------------------------------------

    Line blueLine = new Line(2, "blue");

    blueLine.addStation(new Station("Автозаводская", blueLine));
    blueLine.addStation(
        new Station("Купаловская", blueLine)); //connects to red line's "Октябрьская" station
    blueLine.addStation(new Station("Тракторный завод", blueLine));
    blueLine.addStation(
        new Station("Могилевская", blueLine)); //connects to white line's "Космодесантская" station

    blueStations = blueLine.getStations();

    //-----------------------------------------

    Line whiteLine = new Line(3, "white");

    whiteLine.addStation(
        new Station("Космодесантская", whiteLine)); //connects to blue line's "Могилевская" station
    whiteLine.addStation(new Station("Навохо-доносорово", whiteLine));
    whiteLine.addStation(new Station("Площадь Ильича", whiteLine));
    whiteLine.addStation(new Station("Белорусский вокзал", whiteLine));

    whiteStations = whiteLine.getStations();

    //-----------------------------------------

    stationIndex = new StationIndex();
    stationIndex.addLine(redLine);
    stationIndex.addLine(blueLine);
    stationIndex.addLine(whiteLine);
    stationIndex.stations.addAll(redLine.getStations());
    stationIndex.stations.addAll(blueLine.getStations());
    stationIndex.stations.addAll(whiteLine.getStations());

    TreeSet<Station> redBlueConnection = new TreeSet<>();
    redBlueConnection.add(blueStations.get(1));
    TreeSet<Station> blueRedConnection = new TreeSet<>();
    blueRedConnection.add(redStations.get(3));
    TreeSet<Station> whiteBlueConnection = new TreeSet<>();
    whiteBlueConnection.add(blueStations.get(3));
    TreeSet<Station> blueWhiteConnection = new TreeSet<>();
    blueWhiteConnection.add(whiteStations.get(0));

    stationIndex.connections.put(redStations.get(3), redBlueConnection);
    stationIndex.connections.put(blueStations.get(1), blueRedConnection);
    stationIndex.connections.put(whiteStations.get(0), whiteBlueConnection);
    stationIndex.connections.put(blueStations.get(3), blueWhiteConnection);

    routeCalculator = new RouteCalculator(stationIndex);

  }

  public List<Station> getStationList(String... s) {
    List<Station> expected = new ArrayList<>();
    for (int i = 0; i < s.length; i++) {
      expected.add(stationIndex.getStation(s[i]));
    }

    return expected;
  }

  @After
  @Override
  protected void tearDown() throws Exception {
    redStations = null;
    blueStations = null;
    whiteStations = null;
    routeCalculator = null;
    expectedOnTheLineRoute = null;
    expectedOneConnectionRoute = null;
    expectedTwoConnectionsRoute = null;
  }

  public void test_calculate_duration_on_the_line() {
    assertEquals(7.5, RouteCalculator.calculateDuration(
        getStationList("Могилевская", "Тракторный завод", "Купаловская", "Автозаводская")));
  }

  public void test_calculate_duration_one_connection() {
    assertEquals(8.5, RouteCalculator.calculateDuration(
        getStationList("Восток", "Октябрьская", "Купаловская", "Автозаводская")));
  }

  public void test_calculate_duration_two_connections() {
    assertEquals(19.5, RouteCalculator.calculateDuration(
        getStationList("Навохо-доносорово", "Космодесантская", "Могилевская", "Тракторный завод",
            "Купаловская", "Октябрьская", "Восток", "Борисовский тракт")));
  }


  public void test_get_shortest_route_on_the_line() {
    assertEquals(getStationList("Могилевская", "Тракторный завод", "Купаловская", "Автозаводская"),
        routeCalculator.getShortestRoute(stationIndex.getStation("Могилевская"),
            stationIndex.getStation("Автозаводская")));
  }

  public void test_get_shortest_route_with_one_connection() {
    assertEquals(getStationList("Восток", "Октябрьская", "Купаловская", "Автозаводская"),
        routeCalculator.getShortestRoute(stationIndex.getStation("Восток"),
            stationIndex.getStation("Автозаводская")));
  }

  public void test_get_shortest_route_with_two_connections() {
    assertEquals(
        getStationList("Навохо-доносорово", "Космодесантская", "Могилевская", "Тракторный завод",
            "Купаловская", "Октябрьская", "Восток", "Борисовский тракт"),
        routeCalculator.getShortestRoute(stationIndex.getStation("Навохо-доносорово"),
            stationIndex.getStation("Борисовский тракт")));
  }


}

