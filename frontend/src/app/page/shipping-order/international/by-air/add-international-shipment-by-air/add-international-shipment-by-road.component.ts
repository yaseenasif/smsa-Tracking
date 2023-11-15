import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { LocationService } from 'src/app/page/location/service/location.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { LocationPort } from 'src/app/model/LocationPort';
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

@Component({
  selector: 'app-add-international-shipment-by-road',
  templateUrl: './add-international-shipment-by-road.component.html',
  styleUrls: ['./add-international-shipment-by-road.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddInternationalShipmentByRoadComponent {
  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    arrivalDate: null,
    arrivalTime: null,
    ata: null,
    attachments: null,
    carrier: null,
    departureDate: null,
    departureTime: null,
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
    status: 'Pre-Alert Created',
    tagNumber: null,
    totalShipments: null,
    type: 'By Air',
    vehicleNumber: null,
    vehicleType: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null
  }

  // route:any;
  routes:any;
  items: MenuItem[] | undefined;
  location!:Location[];
  originPorts!:LocationPort[];
  destinationPorts!:LocationPort[];
  drivers!:Driver[]
  vehicleTypes!:VehicleType[]
  shipmentStatus!:ShipmentStatus[];
  selectedDriver:Driver|null=null;
  modeOptions:{ options: string }[] =Object.values(Mode).map(el => ({ options: el }));
  shipmentMode:{ options: string }[] =Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));
  
 
  getLocationPortByLocationForOrigin() {
    this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.originCountry!).subscribe((res)=>{
     this.originPorts=res;  
    },(error)=>{})
  }
  getLocationPortByLocationForDestination() {
    this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.destinationCountry!).subscribe((res)=>{
     this.destinationPorts=res;
    },(error)=>{})
  }

  constructor(private router: Router,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private locationService: LocationService,
    private locationPortService: LocationPortService,
    private driverService: DriverService,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ShipmentStatusService,
    private datePipe: DatePipe) { }
  name!: string;
  checked!: boolean;

  ngOnInit(): void {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Air', routerLink: '/international-shipment-by-air' }, { label: 'Add International Shipment By Air' }];
    this.getAllLocations();
    // this.getAllLocationPort();
    this.getAllDriver();
    this.getAllVehicleType();
    this.getAllShipmentStatus();
  }

  onSubmit() {
    console.log(this.internationalShipment);
    debugger
    this.internationalShipment.etd = this.datePipe.transform(this.internationalShipment.etd, 'yyyy-MM-dd')
    this.internationalShipment.eta = this.datePipe.transform(this.internationalShipment.eta, 'yyyy-MM-dd')
    this.internationalShipment.atd = this.datePipe.transform(this.internationalShipment.atd, 'yyyy-MM-dd')
    this.internationalShipment.ata = this.datePipe.transform(this.internationalShipment.ata, 'yyyy-MM-dd')
    this.internationalShipment.departureDate = this.datePipe.transform(this.internationalShipment.departureDate, 'yyyy-MM-dd')
    this.internationalShipment.arrivalDate = this.datePipe.transform(this.internationalShipment.arrivalDate, 'yyyy-MM-dd')
    this.internationalShipment.departureTime = this.datePipe.transform(this.internationalShipment.departureTime, 'HH:mm:ss')
    this.internationalShipment.arrivalTime = this.datePipe.transform(this.internationalShipment.arrivalTime, 'HH:mm:ss')

    this.internationalShippingService.addInternationalShipment(this.internationalShipment).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is added' });
      setTimeout(() => {
        this.router.navigate(['/international-shipment-by-air']);
      }, 800);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'International Shipment is not added' });
    })
  }

  getAllLocations() {
    this.locationService.getAllLocation().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);
        

    }, error => {
    })
  }

  getInternationalRouteForAir() {
    debugger
    if (this.internationalShipment.originPort !== null && this.internationalShipment.destinationPort !== null) {
      this.internationalShippingService.getInternationalRouteForAir(this.internationalShipment.originPort!, this.internationalShipment.destinationPort!).subscribe((res:any)=>{
        this.routes=res;
        debugger
      },(error:any)=>{
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    }else{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }
  // getAllLocationPort() {
  //   this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
  //     this.locationPort = res.filter(el => el.status)
  //   }, error => { })
  // }

  getAllDriver() {
    this.driverService.getAllDriver().subscribe((res: PaginatedResponse<Driver>) => {

      this.drivers = res.content.filter((el: Driver) => el.status);
    }, error => { })
  }
  getAllVehicleType() {
    this.vehicleTypeService.getALLVehicleType().subscribe((res: VehicleType[]) => {
      this.vehicleTypes = res;
    }, error => {
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res: ShipmentStatus[]) => {
      this.shipmentStatus = res;
    }, error => {
    })
  }

  driverData() {
    this.internationalShipment.driverName = this.selectedDriver?.name;
    this.internationalShipment.driverContact = this.selectedDriver?.contactNumber;
    this.internationalShipment.referenceNumber = this.selectedDriver?.referenceNumber;
  }
}
