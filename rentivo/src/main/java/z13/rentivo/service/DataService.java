package z13.rentivo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import z13.rentivo.repositories.*;
import z13.rentivo.entities.*;

@Service
public class DataService {
    
    private final BillRepository billRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;
    private final DiscountRepository discountRepository;
    private final DriverLicenceRepository driverLicenceRepository;
    private final LocationHistoryRepository locationHistoryRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PenaltyRepository penaltyRepository;
    private final RentalEndRepository rentalEndRepository;
    private final RentalRepository rentalRepository;
    private final RentalStartRepository rentalStartRepository;
    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Autowired
    public DataService(BillRepository billRepository,
                       CarRepository carRepository,
                       ClientRepository clientRepository,
                       CommentRepository commentRepository,
                       DiscountRepository discountRepository,
                       DriverLicenceRepository driverLicenceRepository,
                       LocationHistoryRepository locationHistoryRepository,
                       PaymentRepository paymentRepository,
                       PaymentTypeRepository paymentTypeRepository,
                       PenaltyRepository penaltyRepository,
                       RentalEndRepository rentalEndRepository,
                       RentalRepository rentalRepository,
                       RentalStartRepository rentalStartRepository,
                       SegmentRepository segmentRepository,
                       UserRepository userRepository,
                       UserRoleRepository userRoleRepository) {
                           this.billRepository = billRepository;
                           this.carRepository = carRepository;
                           this.clientRepository = clientRepository;
                           this.commentRepository = commentRepository;
                           this.discountRepository = discountRepository;
                           this.driverLicenceRepository = driverLicenceRepository;
                           this.locationHistoryRepository = locationHistoryRepository;
                           this.paymentRepository = paymentRepository;
                           this.paymentTypeRepository = paymentTypeRepository;
                           this.penaltyRepository = penaltyRepository;
                           this.rentalEndRepository = rentalEndRepository;
                           this.rentalRepository = rentalRepository;
                           this.rentalStartRepository = rentalStartRepository;
                           this.segmentRepository = segmentRepository;
                           this.userRepository = userRepository;
                           this.userRoleRepository = userRoleRepository;
                       }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public List<DriverLicence> getAllDriverLicences() {
        return driverLicenceRepository.findAll();
    }

    public List<LocationHistory> getAllLocationHistories() {
        return locationHistoryRepository.findAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<PaymentType> getAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    public List<RentalEnd> getAllRentalEnds() {
        return rentalEndRepository.findAll();
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<RentalStart> getAllRentalStarts() {
        return rentalStartRepository.findAll();
    }

    public List<Segment> getAllSegments() {
        return segmentRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    public void addSegment(Segment segment) {
        segmentRepository.save(segment);
    }
};