import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from 'src/app/page/location/service/location.service';
import { Location } from 'src/app/model/Location'
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { VehicleType } from 'src/app/model/VehicleType';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { Router } from '@angular/router';
import { Driver } from 'src/app/model/Driver';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-add-domestic-shipping',
  templateUrl: './add-domestic-shipping.component.html',
  styleUrls: ['./add-domestic-shipping.component.scss'],
  providers:[MessageService,DatePipe]
})
export class AddDomesticShippingComponent {
  items: MenuItem[] | undefined;
  routes:any;

  domesticShipment:DomesticShipment={
    originFacility: null,
    originLocation: null,
    refrigeratedTruck: false,
    destinationFacility: null,
    destinationLocation: null,
    routeNumber: null,
    numberOfShipments: null,
    weight: null,
    etd: null,
    eta: null,
    atd: null,
    driverName: null,
    driverContact: null,
    referenceNumber: null,
    vehicleType: null,
    numberOfPallets: null,
    numberOfBags: null,
    vehicleNumber: null,
    tagNumber: null,
    sealNumber: null,
    status: 'Pre-Alert Created',
    remarks: null,
    ata: null,
    totalShipments: null,
    overages: null,
    overagesAwbs: null,
    received: null,
    shortages: null,
    shortagesAwbs: null,
    attachments: null,
    arrivalTime: null,
    departureTime: null,
    preAlertNumber: null
  };

  location!:Location[];
  selectedLocation!:Location;
  selectedOriginLocation!:Location;
  selectedDestinationLocation!:Location;


  originFacility!:originFacility[];
  selectedOriginFacility!:originFacility;
  selectedDestinationFacility!:originFacility;

  vehicleTypes!:VehicleType[];
  selectedVehicleTypes!:VehicleType;

  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));


  shipmentStatus!:ShipmentStatus[];
  selectedShipmentStatus!:ShipmentStatus;
  minETDDate: Date = new Date();

  drivers!:Driver[]

  constructor(private locationService: LocationService,
    private vehicleTypeService:VehicleTypeService,
    private shipmentStatusService:ShipmentStatusService,
    private messageService: MessageService,
    private domesticShipmentService:DomesticShippingService,
    private driverService:DriverService,
    private router:Router,
    private datePipe:DatePipe) { }
  name!:string;
  checked!:boolean;
  size=100000
  uploadedFiles: any[] = [];
  fromDate:any;
  selectedDriver:Driver|null=null;


  flag=false;


  onUpload(event: any) {

  }

  onUpload1(event:any) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }

  ngOnInit(): void {


    this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'Add Domestic Shipment'}];
    this.getAllLocations();
    this.getAllVehicleType();
    this.getAllShipmentStatus();
    this.getAllDriver();

    this.originFacility=[
      {
        originFacility:"HUB"
      },
      {
        originFacility:"Station"
      },
      {
        originFacility:"Gateway"
      }
    ]

  }

  getDomesticRoute() {
    this.routes=[]

    if (this.domesticShipment.originLocation !== null && this.domesticShipment.destinationLocation !== null) {
      this.domesticShipmentService.getDomesticRoute(this.domesticShipment.originLocation!, this.domesticShipment.destinationLocation!).subscribe((res:any)=>{
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

  getAllLocations(){
    this.locationService.getAllLocationForDomestic().subscribe((res:Location[])=>{
      this.location=res.filter(el => el.status);
    },error=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  getAllVehicleType(){
    this.vehicleTypeService.getALLVehicleType().subscribe((res:VehicleType[])=>{
      this.vehicleTypes=res;
    },error=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
   }

   getAllShipmentStatus(){
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res:ShipmentStatus[])=>{
      this.shipmentStatus=res;
    },error=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
   }

   addDomesticShipment(domesticShipment:DomesticShipment){
      this.domesticShipmentService.addDomesticShipment(domesticShipment).subscribe((res:DomesticShipment)=>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Shipment Added Successfully' });
        setTimeout(() => {
          this.router.navigate(['/domestic-shipping']);
        },800);
      },(error:any)=>{
        if(error.error.body){
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        }else{
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
        }
      })
   }

   getAllDriver(){
    this.driverService.getAllDriver().subscribe((res:PaginatedResponse<Driver>)=>{

     this.drivers=res.content.filter((el:Driver)=>el.status);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   driverData(){
    this.domesticShipment.driverName=this.selectedDriver?.name;
    this.domesticShipment.driverContact=this.selectedDriver?.contactNumber;
    this.domesticShipment.referenceNumber=this.selectedDriver?.referenceNumber;
   }

   onSubmit(){
    this.domesticShipment.departureTime = this.datePipe.transform(this.domesticShipment.departureTime, 'HH:mm:ss')
    this.domesticShipment.arrivalTime = this.datePipe.transform(this.domesticShipment.arrivalTime, 'HH:mm:ss')
    this.domesticShipment.etd=this.datePipe.transform(this.domesticShipment.etd,'yyyy-MM-dd')
    this.domesticShipment.eta=this.datePipe.transform(this.domesticShipment.eta,'yyyy-MM-dd')
    this.domesticShipment.atd=this.datePipe.transform(this.domesticShipment.atd,'yyyy-MM-dd')
    this.domesticShipment.ata=this.datePipe.transform(this.domesticShipment.ata,'yyyy-MM-dd')
    this.addDomesticShipment(this.domesticShipment);
   }

   onETDDateSelected(selectedETDDate: any) {

    this.minETDDate = selectedETDDate;
  }
}


interface originFacility{
  originFacility:string
}




