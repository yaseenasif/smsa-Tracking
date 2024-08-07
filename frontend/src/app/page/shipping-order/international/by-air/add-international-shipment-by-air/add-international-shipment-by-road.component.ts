import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { LocationService } from 'src/app/page/location/service/location.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { DriverService } from 'src/app/page/driver/service/driver.service';
// import { LocationPort } from 'src/app/model/LocationPort';
import { Location } from 'src/app/model/Location';
import { Driver } from 'src/app/model/Driver';
import { VehicleType } from 'src/app/model/VehicleType';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { Mode } from 'src/app/model/Mode';
import { ShipmentMode } from 'src/app/model/ShipmentMode';
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { DatePipe } from '@angular/common';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';

@Component({
  selector: 'app-add-international-shipment-by-road',
  templateUrl: './add-international-shipment-by-road.component.html',
  styleUrls: ['./add-international-shipment-by-road.component.scss'],
  providers: [MessageService, DatePipe],
})
export class AddInternationalShipmentByRoadComponent {
  defaultDate:Date=new Date(this.datePipe.transform((new Date()).setHours(0, 0, 0, 0),'EEE MMM dd yyyy HH:mm:ss \'GMT\'ZZ (z)')!)
  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    ata: null,
    attachments: null,
    carrier: null,
    destinationCountry: null,
    driverContact: null,
    driverName: null,
    flightNumber: null,
    numberOfBags: null,
    numberOfPallets: null,
    numberOfShipments: null,
    originCountry: null,
    overageAWBs: null,
    overages: null,
    preAlertNumber: null,
    received: null,
    referenceNumber: null,
    refrigeratedTruck: false,
    remarks: null,
    sealNumber: null,
    shipmentMode: null,
    shortageAWBs: null,
    shortages: null,
    status: 'Created',
    tagNumber: null,
    totalShipments: null,
    type: 'By Air',
    vehicleNumber: null,
    vehicleType: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null,
    trip: null,
    preAlertType: null,
    transitTimeTaken: null,
    originFacility: null,
    originLocation: null,
    destinationFacility: null,
    destinationLocation: null,
    numberOfBoxes: undefined,
    damage: null,
    damageAwbs: null,
    numberOfPalletsReceived: null,
    numberOfBagsReceived: null
  };

  // route:any;
  routes: any;
  items: MenuItem[] | undefined;
  location!: Location[];
  // originPorts!: LocationPort[];
  // destinationPorts!: LocationPort[];
  drivers!: Driver[];
  vehicleTypes!: VehicleType[];
  shipmentStatus!: ProductField;
  selectedDriver: Driver | null = null;
  modeOptions: { options: string }[] = Object.values(Mode).map((el) => ({
    options: el,
  }));
  shipmentMode: { options: string }[] = Object.values(ShipmentMode).map(
    (el) => ({ options: el })
  );
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets)
    .filter((value) => typeof value === 'number')
    .map((value) => ({ options: value as number }));
  minDate: Date = new Date();
  carrier: ProductField | undefined | null;
  user!: User;
  originCountry!: Country[];
  destinationCountry!: Country[];
  originFacility: (Facility|null|undefined)[]|undefined;
  destinationFacility:(Facility|null|undefined)[]|undefined
  orgLocation: Location[] | undefined;
  desLocation: Location[] | undefined;

  getLoggedInUser() {
    this.userService.getLoggedInUser().subscribe(
      (res: User) => {
        this.user = res;
        this.originCountry = [];
        this.destinationCountry = [];
        res.internationalAirOriginLocation?.forEach((el) => {
          return this.originCountry.push(el.country!);
        });
        this.originCountry = this.originCountry.filter(
          (obj, index, arr) =>
            index === arr.findIndex((item: Country) => item.id === obj.id)
        );

        res.internationalAirDestinationLocation?.forEach((el) => {
          return this.destinationCountry.push(el.country!);
        });
        this.destinationCountry = this.destinationCountry.filter(
          (obj, index, arr) =>
            index === arr.findIndex((item: Country) => item.id === obj.id)
        );
      },
      (error) => {
        if (error.error.body) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.body,
          });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error,
          });
        }
      }
    );
  }

  // getLocationPortByLocationForOrigin() {
  //   this.internationalShippingService
  //     .getLocationPortByLocation(this.internationalShipment.originCountry!)
  //     .subscribe(
  //       (res) => {
  //         this.originPorts = res;
  //       },
  //       (error) => {
  //         this.messageService.add({
  //           severity: 'error',
  //           summary: 'Error',
  //           detail: error.error.body,
  //         });
  //       }
  //     );
  // }
  // getLocationPortByLocationForDestination() {
  //   this.internationalShippingService
  //     .getLocationPortByLocation(this.internationalShipment.destinationCountry!)
  //     .subscribe(
  //       (res) => {
  //         this.destinationPorts = res;
  //       },
  //       (error) => {
  //         this.messageService.add({
  //           severity: 'error',
  //           summary: 'Error',
  //           detail: error.error.body,
  //         });
  //       }
  //     );
  // }

  constructor(
    private router: Router,
    private userService: UserService,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private locationService: LocationService,
    private shipmentStatusService: ProductFieldServiceService,
    // private locationPortService: LocationPortService,
    private driverService: DriverService,
    private vehicleTypeService: VehicleTypeService,
    // private shipmentStatusService: ProductFieldServiceService,
    private datePipe: DatePipe
  ) {}
  name!: string;
  checked!: boolean;

  ngOnInit(): void {
    this.items = [
      { label: 'International Outbound', routerLink: '/international-tile' },
      {
        label: 'International Outbound By Air',
        routerLink: '/international-shipment-by-air',
      },
      { label: 'Add International Outbound By Air' },
    ];
    this.getAllLocations();
    // this.getAllLocationPort();
    this.getAllDriver();
    this.getAllVehicleType();
    this.getAllShipmentCarrier();
    this.getLoggedInUser();
    // this.getAllShipmentStatus();
  }

  onSubmit() {
    let orgLocation=this.user.internationalAirOriginLocation?.find((el)=>{return el.country?.name == this.internationalShipment.originCountry && el.facility?.name==this.internationalShipment.originFacility && el.locationName==this.internationalShipment.originLocation});
    let desLocation=this.user.internationalAirDestinationLocation?.find((el)=>{return el.country?.name == this.internationalShipment.destinationCountry && el.facility?.name==this.internationalShipment.destinationFacility && el.locationName==this.internationalShipment.destinationLocation});

    let orgLocationId= orgLocation ? orgLocation.id : null;
    let desLocationId= desLocation ? desLocation.id : null;
    if(orgLocationId && desLocationId){
      this.internationalShipment.etd = this.datePipe.transform(
        this.internationalShipment.etd,
        'yyyy-MM-ddTHH:mm:ss'
      );
      this.internationalShipment.eta = this.datePipe.transform(
        this.internationalShipment.eta,
        'yyyy-MM-ddTHH:mm:ss'
      );
      this.internationalShipment.atd = this.datePipe.transform(
        this.internationalShipment.atd,
        'yyyy-MM-ddTHH:mm:ss'
      );
      this.internationalShipment.ata = this.datePipe.transform(
        this.internationalShipment.ata,
        'yyyy-MM-ddTHH:mm:ss'
      );
  
      this.internationalShippingService
        .addInternationalShipment(this.internationalShipment,orgLocationId!,desLocationId!)
        .subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'International Outbound is added',
            });
            setTimeout(() => {
              this.router.navigate(['/international-shipment-by-air']);
            }, 800);
          },
          (error) => {
            if (error.error.body) {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: error.error.body,
              });
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: error.error,
              });
            }
            this.internationalShipment.etd = this.internationalShipment.etd
              ? new Date(this.internationalShipment.etd)
              : null;
            this.internationalShipment.eta = this.internationalShipment.eta
              ? new Date(this.internationalShipment.eta)
              : null;
            this.internationalShipment.atd = this.internationalShipment.atd
              ? new Date(this.internationalShipment.atd)
              : null;
            this.internationalShipment.ata = this.internationalShipment.ata
              ? new Date(this.internationalShipment.ata)
              : null;
          }
        );
    }
    else{
      if (!orgLocationId) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Origin location not found.' });
      }
      if (!desLocationId) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Destination location not found.' });
      }
    }

   
  }

  getAllLocations() {
    this.locationService.getAllLocationForInternational().subscribe(
      (res: Location[]) => {
        this.location = res.filter((el) => el.status);
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error.body,
        });
      }
    );
  }

  getInternationalRouteForAir() {
    this.routes = [];

    if (
      this.internationalShipment.originLocation !== null &&
      this.internationalShipment.destinationLocation !== null &&
      this.internationalShipment.trip !== null
    ) {
      this.internationalShippingService
        .getInternationalRouteForAir(
          this.internationalShipment.originLocation!,
          this.internationalShipment.destinationLocation!,
          this.internationalShipment.trip!
        )
        .subscribe(
          (res: any) => {
            this.routes = res;
          },
          (error: any) => {
            console.log(error);
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error.body,
            });
          }
        );
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'You must have to select origin and destination port',
      });
    }
  }
  // getAllLocationPort() {
  //   this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
  //     this.locationPort = res.filter(el => el.status)
  //   }, error => { })
  // }

  getAllDriver() {
    this.driverService.getAllDriver().subscribe(
      (res: PaginatedResponse<Driver>) => {
        this.drivers = res.content.filter((el: Driver) => el.status);
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error.body,
        });
      }
    );
  }
  getAllVehicleType() {
    this.vehicleTypeService.getALLVehicleType().subscribe(
      (res: VehicleType[]) => {
        this.vehicleTypes = res;
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error.body,
        });
      }
    );
  }

  getAllShipmentCarrier() {
    return this.shipmentStatusService
      .getProductFieldByName('Carrier')
      .subscribe(
        (res) => {
          this.carrier = res;
        },
        (error) => {}
      );
  }

  // getAllShipmentStatus() {
  //   this.shipmentStatusService.getProductFieldByName("Auto_Status").subscribe((res: ProductField) => {
  //     for (const list of res.productFieldValuesList) {
  //       this.internationalShipment.status = list.name;
  //     }
  //   }, error => {
  //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //   })
  // }

  driverData() {
    this.internationalShipment.driverName = this.selectedDriver?.name;
    this.internationalShipment.driverContact =
      this.selectedDriver?.contactNumber;
    this.internationalShipment.referenceNumber =
      this.selectedDriver?.referenceNumber;
  }

  flag = false;
  dashAfterThree() {
    let charToAdd = '-';
    if (this.internationalShipment.preAlertNumber!.length === 3) {
      this.flag = true;
    }
    if (this.internationalShipment.preAlertNumber!.length === 4 && this.flag) {
      this.internationalShipment.preAlertNumber =
        this.internationalShipment.preAlertNumber!.slice(0, 3) +
        charToAdd +
        this.internationalShipment.preAlertNumber!.slice(3);
      this.flag = false;
    }
  }

  onOrgCountryChange() {

      this.originFacility = [];
      this.originFacility = this.user.internationalAirOriginLocation
    ?.filter((el) => el.country?.name === this.internationalShipment.originCountry )
    .map(el => el.facility);
    this.originFacility=this.originFacility?.filter((obj, index, self) =>
    index === self.findIndex((o) => o!.id === obj!.id)
    );
    this.internationalShipment.originFacility=null; 
    this.orgLocation=[]; 
  }

  onDesCountryChange() {
      this.destinationFacility = [];
      this.destinationFacility=this.user.internationalAirDestinationLocation
      ?.filter((el) => el.country?.name === this.internationalShipment.destinationCountry )
      .map(el => el.facility);
      this.destinationFacility=this.destinationFacility?.filter((obj, index, self) =>
      index === self.findIndex((o) => o!.id === obj!.id)
      );    
      this.internationalShipment.destinationFacility=null; 
      this.desLocation=[]; 
  }

  onOrgFacilityChange() {
    this.orgLocation=this.user.internationalAirOriginLocation?.filter((el)=> el.country?.name==this.internationalShipment.originCountry && el.facility?.name==this.internationalShipment.originFacility)
  }
  onDesFacilityChange() {
    this.desLocation=this.user.internationalAirDestinationLocation?.filter((el)=> el.country?.name==this.internationalShipment.destinationCountry && el.facility?.name==this.internationalShipment.destinationFacility)
  }
}
