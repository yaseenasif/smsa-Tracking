import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/page/location/service/location.service';
import { Location } from '../../../../../model/Location'
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';
import { LocationPort } from 'src/app/model/LocationPort';
import { Mode } from 'src/app/model/Mode';
import { ShipmentMode } from 'src/app/model/ShipmentMode';
import { Driver } from 'src/app/model/Driver';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { VehicleType } from 'src/app/model/VehicleType';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { User } from 'src/app/model/User';
import { Country } from 'src/app/model/Country';
import { UserService } from 'src/app/page/user/service/user.service';

@Component({
  selector: 'app-add-international-shipping',
  templateUrl: './add-international-shipping.component.html',
  styleUrls: ['./add-international-shipping.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddInternationalShippingComponent {

  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    ata: null,
    attachments: null,
    carrier: null,
    destinationCountry: null,
    destinationPort: null,
    driverContact: null,
    driverName: null,
    flightNumber: null,
    numberOfBags: null,
    numberOfPallets: null,
    numberOfShipments: null,
    originCountry: null,
    originPort: null,
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
    status: "Created",
    tagNumber: null,
    totalShipments: null,
    type: 'By Road',
    vehicleNumber: null,
    vehicleType: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null,
    trip: null,
    preAlertType: null,
    transitTimeTaken: null
  }

  routes: any;
  items: MenuItem[] | undefined;
  location!: Location[];
  originPorts!: LocationPort[];
  destinationPorts!: LocationPort[];
  drivers!: Driver[]
  vehicleTypes!: VehicleType[]
  shipmentStatus!: ProductField;
  selectedDriver: Driver | null = null;
  modeOptions: { options: string }[] = Object.values(Mode).map(el => ({ options: el }));
  shipmentMode: { options: string }[] = Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));
  minDate: Date = new Date();




  constructor(private router: Router,
    private userService:UserService,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private locationService: LocationService,
    private locationPortService: LocationPortService,
    private driverService: DriverService,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ProductFieldServiceService,
    private datePipe: DatePipe) { }
  name!: string;
  checked!: boolean;
  size = 100000
  uploadedFiles: any[] = [];
  user!: User;
  originCountry!: Country[];
  destinationCountry!:Country[];

  getLoggedInUser(){
    this.userService.getLoggedInUser().subscribe((res: User) => {
      this.user=res;
      this.originCountry=[];
      this.destinationCountry=[];
      res.internationalRoadOriginLocation?.forEach((el)=>{
        return this.originCountry.push(el.facility?.country!);
      })
      this.originCountry = this.originCountry.filter((obj, index, arr) =>
      index === arr.findIndex((item:Country) => item.id === obj.id)
      );
      

      res.internationalRoadDestinationLocation?.forEach((el)=>{
        return this.destinationCountry.push(el.facility?.country!);
      })
      this.destinationCountry = this.destinationCountry.filter((obj, index, arr) =>
      index === arr.findIndex((item:Country) => item.id === obj.id)
      );
      
      
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
} 

  getLocationPortByLocationForOrigin() {
    this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.originCountry!).subscribe((res) => {
      this.originPorts = res;
    }, (error) => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getLocationPortByLocationForDestination() {
    this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.destinationCountry!).subscribe((res) => {
      this.destinationPorts = res;
    }, (error) => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  ngOnInit(): void {


    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Road', routerLink: '/international-shipment-by-road' }, { label: 'Add International Shipment By Road' }];
    this.getAllLocations();
    // this.getAllLocationPort();
    this.getAllDriver();
    this.getAllVehicleType();
    // this.getAllShipmentStatus();
  }

  onSubmit() {
    this.internationalShipment.etd = this.datePipe.transform(this.internationalShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.eta = this.datePipe.transform(this.internationalShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.atd = this.datePipe.transform(this.internationalShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.ata = this.datePipe.transform(this.internationalShipment.ata, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShippingService.addInternationalShipment(this.internationalShipment).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is added' });
      setTimeout(() => {
        this.router.navigate(['/international-shipment-by-road']);
      }, 800);
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
      this.internationalShipment.etd=this.internationalShipment.etd?new Date(this.internationalShipment.etd):null;
      this.internationalShipment.eta=this.internationalShipment.eta?new Date(this.internationalShipment.eta):null;
      this.internationalShipment.atd=this.internationalShipment.atd?new Date(this.internationalShipment.atd):null;
      this.internationalShipment.ata=this.internationalShipment.ata?new Date(this.internationalShipment.ata):null;
     })
  }

  getInternationalRouteForRoad() {
    this.routes = []
    if (this.internationalShipment.originPort !== null && this.internationalShipment.destinationPort !== null && this.internationalShipment.trip !== null) {
      this.internationalShippingService.getInternationalRouteForRoad(this.internationalShipment.originPort!, this.internationalShipment.destinationPort!, this.internationalShipment.trip!).subscribe((res: any) => {
        this.routes = res;

      }, (error: any) => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }

  getAllLocations() {
    this.locationService.getAllLocationForInternational().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  // getAllLocationPort(){
  //   this.locationPortService.getAllLocationPort().subscribe((res:LocationPort[])=>{
  //     this.locationPort=res.filter(el=>el.status)
  //   },error=>{})
  // }
  getAllDriver() {
    this.driverService.getAllDriver().subscribe((res: PaginatedResponse<Driver>) => {

      this.drivers = res.content.filter((el: Driver) => el.status);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getAllVehicleType() {
    this.vehicleTypeService.getALLVehicleType().subscribe((res: VehicleType[]) => {
      this.vehicleTypes = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  // getAllShipmentStatus() {
  //   this.shipmentStatusService.getProductFieldByName("Auto_Status").subscribe((res: ProductField) => {
  //     for (const productFieldValue of res.productFieldValuesList) {
  //       this.internationalShipment.status = productFieldValue.name;
  //     }
  //   }, error => {
  //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //   })
  // }

  driverData() {
    this.internationalShipment.driverName = this.selectedDriver?.name;
    this.internationalShipment.driverContact = this.selectedDriver?.contactNumber;
    this.internationalShipment.referenceNumber = this.selectedDriver?.referenceNumber;
  }



  flag = false;
  dashAfterThree() {
    let charToAdd = "-";
    if (this.internationalShipment.preAlertNumber!.length === 3) {
      this.flag = true;
    }
    if (this.internationalShipment.preAlertNumber!.length === 4 && this.flag) {
      this.internationalShipment.preAlertNumber = this.internationalShipment.preAlertNumber!.slice(0, 3) + charToAdd + this.internationalShipment.preAlertNumber!.slice(3);
      this.flag = false;
    }
  }

}



