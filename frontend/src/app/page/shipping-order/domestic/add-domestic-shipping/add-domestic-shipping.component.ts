import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { LocationService } from 'src/app/page/location/service/location.service';
import { Location } from 'src/app/model/Location'
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { VehicleType } from 'src/app/model/VehicleType';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-domestic-shipping',
  templateUrl: './add-domestic-shipping.component.html',
  styleUrls: ['./add-domestic-shipping.component.scss']
})
export class AddDomesticShippingComponent {
  items: MenuItem[] | undefined;

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
    status: null,
    remarks: null,
    ata: null,
    totalShipments: null,
    overages: null,
    overagesAwbs: null,
    received: null,
    shortages: null,
    shortagesAwbs: null,
    attachments: null
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

  noOfPallets!:noOfPallets[];
  selectedNoOfPallets!:noOfPallets;

  shipmentStatus!:ShipmentStatus[];
  selectedShipmentStatus!:ShipmentStatus;

  constructor(private locationService: LocationService,
    private vehicleTypeService:VehicleTypeService,
    private shipmentStatusService:ShipmentStatusService,
    private domesticShipmentService:DomesticShippingService,
    private router:Router) { }
  name!:string;
  checked!:boolean;
  size=100000
  uploadedFiles: any[] = [];
  fromDate:any;

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

    this.noOfPallets=[
      {
        number:30
      },
      {
        number:29
      },
      {
        number:28
      },
      {
        number:27
      },
      {
        number:26
      },
      {
        number:25
      },
      {
        number:24
      },
      {
        number:23
      },
      {
        number:22
      },
      {
        number:21
      },
      {
        number:20
      },
      {
        number:19
      },
      {
        number:18
      },
      {
        number:17
      },
      {
        number:16
      },
      {
        number:15
      },
      {
        number:14
      },
      {
        number:13
      },
      {
        number:12
      },
      {
        number:11
      },
      {
        number:10
      },
      {
        number:9
      },
      {
        number:8
      },
      {
        number:7
      },
      {
        number:6
      },
      {
        number:5
      },
      {
        number:4
      },
      {
        number:3
      },
      {
        number:2
      },
      {
        number:1
      }
    ]
  }

  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.location=res.filter(el => el.status);   
    },error=>{
    })
  }

  getAllVehicleType(){
    this.vehicleTypeService.getALLVehicleType().subscribe((res:VehicleType[])=>{
      this.vehicleTypes=res;  
    },error=>{
    })
   }

   getAllShipmentStatus(){
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res:ShipmentStatus[])=>{
      this.shipmentStatus=res; 
    },error=>{
    })
   }

   addDomesticShipment(domesticShipment:DomesticShipment){
      this.domesticShipmentService.addDomesticShipment(domesticShipment).subscribe((res:DomesticShipment)=>{
        console.log("DomesticShipmentAdded");
        this.router.navigate(['/domestic-shipping']);
      },(error:any)=>{
        console.log("Getting error wile adding domestic Shipment");
        
      })
   }

   onSubmit(){
    // this.addDomesticShipment.originFacility = this.selectedOriginFacility.originFacility;
    console.log(this.domesticShipment);
    console.log(this.domesticShipment.originFacility);
    debugger
    this.addDomesticShipment(this.domesticShipment);
    
   }






}


interface originFacility{
  originFacility:string
}

interface noOfPallets{
  number:number;
}


