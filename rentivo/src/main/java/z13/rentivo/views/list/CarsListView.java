package z13.rentivo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Car;
import z13.rentivo.entities.Segment;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.security.PermitAll;
@PermitAll
@PageTitle("List of all cars")
@Route(value = "/carsList", layout = MainLayout.class)
public class CarsListView extends VerticalLayout {
    private final DataService dataService;
    CarFilter carFilter;
    SegmentFilter segmentFilter;
    TransmissionFilter transmissionFilter;
    FuelTypeFilter fuelTypeFilter;
    SeatsFilter seatsFilter;
    private final ComboBox<Segment> segmentCB;
    private final TextField textField;
    private final ComboBox<String> transmissionCB;
    private final ComboBox<String> fuelTypeCB;
    private final ComboBox<Integer> seatsCB;

    Grid<Car> grid = new Grid<>(Car.class, false);

    @Autowired
    public CarsListView(DataService dataService) {
        this.dataService = dataService;
        textField = new TextField("Custom search");
        segmentCB = new ComboBox<Segment>("Car segment");
        transmissionCB = new ComboBox<String>("Transmission");
        fuelTypeCB = new ComboBox<String>("Fuel Type");
        seatsCB = new ComboBox<Integer>("Seats");

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(dataService), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("cars-grid");
        grid.setSizeFull();

        grid.addComponentColumn(Car -> createStatusIcon(Car.getIsAvailableForRent()))
                .setTooltipGenerator(Car -> getStatus(Car.getIsAvailableForRent()))
                .setHeader("Available");
        grid.addColumn(Car::getBrand).setHeader("Brand").setSortable(true);
        grid.addColumn(Car::getModel).setHeader("Model").setSortable(true);
        grid.addColumn(Car::getProductionYear).setHeader("Year").setSortable(true);
        grid.addColumn(Car::getFuelType).setHeader("Fuel Type").setSortable(true);
        grid.addColumn(Car::getTransmission).setHeader("Transmission").setSortable(true);
        grid.addColumn(Car::getSeats).setHeader("Seats").setSortable(true);
        grid.addColumn(Car::getFuelCapacity).setHeader("Fuel Capacity").setSortable(true);
        grid.addColumn(Car::getMileage).setHeader("Mileage").setSortable(true);
        grid.addColumn(Car::getRegistrationNumber).setHeader("Ro. Number").setSortable(true);
        grid.addColumn(Car -> Car.getSegment().getName()).setHeader("Segment").setSortable(true);

        //TODO Add Comments Pop-up

        List<Car> listOfCars = dataService.getAllCars();
        GridListDataView<Car> dataView = grid.setItems(listOfCars);

        carFilter = new CarFilter(dataView);
        segmentFilter = new SegmentFilter(dataView);
        transmissionFilter = new TransmissionFilter(dataView);
        seatsFilter = new SeatsFilter(dataView);
        fuelTypeFilter = new FuelTypeFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }



    private static class CarFilter {
        private final GridListDataView<Car> dataView;
        private String nameInput;

        public CarFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);

        }
        public void setlInput(String nameInput) {
            this.nameInput = nameInput;
            this.dataView.refreshAll();
        }
        public boolean test(Car car) {
            boolean matchesBrand = matches(car.getBrand(), nameInput);
            boolean matchesModel = matches(car.getModel(), nameInput);
            boolean matchesFuelType = matches(car.getFuelType(), nameInput);
            return matchesBrand || matchesModel || matchesFuelType;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
    private static class SegmentFilter {
        private final GridListDataView<Car> dataView;
        private String segmentName;

        public SegmentFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setSegment(String segmentName) {
            this.segmentName = segmentName;
            this.dataView.refreshAll();
        }

        public boolean test(Car car) {
            boolean matchesSegment = matches(car.getSegment().getName(), segmentName);
            return matchesSegment;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
    private static class TransmissionFilter {
        private final GridListDataView<Car> dataView;
        private String transmissionName;

        public TransmissionFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setTransmission(String transmissionName) {
            this.transmissionName = transmissionName;
            this.dataView.refreshAll();
        }

        public boolean test(Car car) {
            boolean matchesTransmission= matches(car.getTransmission(), transmissionName);
            return matchesTransmission;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
    private static class FuelTypeFilter {
        private final GridListDataView<Car> dataView;
        private String fuelTypeName;

        public FuelTypeFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setFuelType(String fuelTypeName) {
            this.fuelTypeName = fuelTypeName;
            this.dataView.refreshAll();
        }

        public boolean test(Car car) {
            boolean matchesFuelType = matches(car.getFuelType(), fuelTypeName);
            return matchesFuelType;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
    private static class SeatsFilter {
        private final GridListDataView<Car> dataView;
        private Integer seatsAmount;

        public SeatsFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setSeats(Integer seats) {
            this.seatsAmount = seats;
            this.dataView.refreshAll();
        }

        public boolean test(Car car) {
            boolean matchesSegment = matches(car.getSeats(), seatsAmount);
            return matchesSegment;
        }
        private boolean matches(Integer value, Integer restrictionValue) {
            return (restrictionValue == null || restrictionValue == 0 ||
                   value == restrictionValue);
        }
    }



    private static Component createTextFilter(String labelText,
                                          TextField textField,
                                          Consumer<String> filterChangeConsumer) {

        textField.setLabel(labelText);
        textField.setPlaceholder("Brand or model...");;
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));


        VerticalLayout layout = new VerticalLayout(textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }
    private static Component createSegmentFilter(String labelText,
                                              ComboBox<Segment> segmentCB,
                                              Consumer<String> filterChangeConsumer,
                                              DataService dataService) {

        segmentCB.setLabel(labelText);
        segmentCB.setItems(dataService.getAllSegments());
        segmentCB.setItemLabelGenerator(Segment::getName);
        segmentCB.setClearButtonVisible(true);

        segmentCB.addValueChangeListener(e -> {
            Object value = e.getValue();
            String name;

            if (value instanceof Segment) {
                name = ((Segment) value).getName();
            } else {
                name = (String) value;
            }

            filterChangeConsumer.accept(name);
        });


        VerticalLayout layout = new VerticalLayout(segmentCB);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static Component createTransmissionFilter(String labelText,
                                              ComboBox<String> transmissionCB,
                                              Consumer<String> filterChangeConsumer) {

        transmissionCB.setLabel(labelText);
        List <String> transmissions =  Arrays.asList("automatyczna", "manualna");
        transmissionCB.setItems(transmissions);
        transmissionCB.setClearButtonVisible(true);


        transmissionCB.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));


        VerticalLayout layout = new VerticalLayout(transmissionCB);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static Component createFuelTypeFilter(String labelText,
                                                  ComboBox<String> fuelTypeCB,
                                                  Consumer<String> filterChangeConsumer) {

        fuelTypeCB.setLabel(labelText);
        List <String> fuelTypes =  Arrays.asList("benzyna", "diesel", "LPG", "hybryda", "elektryczny");
        fuelTypeCB.setItems(fuelTypes);
        fuelTypeCB.setClearButtonVisible(true);

        fuelTypeCB.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));


        VerticalLayout layout = new VerticalLayout(fuelTypeCB);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static Component createSeatsFilter(String labelText,
                                               ComboBox<Integer> seatsCB,
                                               Consumer<Integer> filterChangeConsumer) {

        seatsCB.setLabel(labelText);
        List <Integer> seats =  Arrays.asList(2,3,4,5,6,7,8,9);
        seatsCB.setItems(seats);
        seatsCB.setClearButtonVisible(true);

        seatsCB.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));


        VerticalLayout layout = new VerticalLayout(seatsCB);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }


    private HorizontalLayout getToolbar(DataService dataService) {

        Component filterText = createTextFilter("Custom search.", textField, carFilter::setlInput);
        Component filterSegment = createSegmentFilter("Segment", segmentCB, segmentFilter::setSegment, dataService);
        Component filterSeats = createSeatsFilter("Seats", seatsCB, seatsFilter::setSeats);
        Component filterFuelTypes= createFuelTypeFilter("Fuel Type", fuelTypeCB, fuelTypeFilter::setFuelType);
        Component filterTransmission = createTransmissionFilter("Transmission", transmissionCB, transmissionFilter::setTransmission);

        Button clearButton = new Button();
        clearButton.setWidthFull();
        clearButton.setText("Reset filters");
        clearButton.addClickListener(click ->{
            textField.clear();
            segmentFilter.setSegment("");
            segmentCB.clear();
            fuelTypeCB.clear();
            transmissionCB.clear();
            seatsCB.clear();
        });
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.getStyle().set("max-width", "100%");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, filterSegment,filterSeats, filterFuelTypes,
                    filterTransmission);
        toolbar.add(clearButton);
        toolbar.setAlignSelf(FlexComponent.Alignment.END, clearButton);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private Icon createStatusIcon(Boolean isAvailable) {
        Icon icon;
        if (isAvailable) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

    private String getStatus(Boolean isAvailable){
        if (isAvailable) {
            return "Available";
        }   else{
            return "Busy";
        }
    }

}