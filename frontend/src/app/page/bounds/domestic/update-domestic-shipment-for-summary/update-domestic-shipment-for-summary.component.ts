import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { VehicleType } from 'src/app/model/VehicleType';
import { LocationService } from 'src/app/page/location/service/location.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { Location } from 'src/app/model/Location'

@Component({
  selector: 'app-update-domestic-shipment-for-summary',
  templateUrl: './update-domestic-shipment-for-summary.component.html',
  styleUrls: ['./update-domestic-shipment-for-summary.component.scss'],
  providers:[MessageService]
})
export class UpdateDomesticShipmentForSummaryComponent {
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

  domesticShipmentId:any;

  constructor(private locationService: LocationService,
    private vehicleTypeService:VehicleTypeService,
    private shipmentStatusService:ShipmentStatusService,
    private domesticShipmentService:DomesticShippingService,
    private router:Router,
    private messageService: MessageService,
    private route:ActivatedRoute) { }
  name!:string;
  checked!:boolean;
  size=100000
  uploadedFiles: any[] = [];

  onUpload(event: any) {
    
  }

  onUpload1(event:any) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }
  
  ngOnInit(): void {
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.domesticShipmentById(this.domesticShipmentId);

    this.items = [{ label: 'Domestic Summary',routerLink:'/domestic-summary'},{ label: 'Edit Domestic Shipment'}];
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

   domesticShipmentById(id:number){
    this.domesticShipmentService.getDomesticShipmentById(id).subscribe((res:DomesticShipment)=>{
      this.domesticShipment=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }         
    })
   }

   updateDomesticShipment(domesticShipment:DomesticShipment){
      this.domesticShipmentService.updateDomesticShipment(this.domesticShipmentId,domesticShipment).subscribe((res:DomesticShipment)=>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Shipment Updated Successfully' });
        debugger
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

   onSubmit(){
    this.updateDomesticShipment(this.domesticShipment);
   }






}


interface originFacility{
  originFacility:string
}

interface noOfPallets{
  number:number;
}


