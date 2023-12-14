import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { VehicleType } from 'src/app/model/VehicleType';
import { LocationService } from 'src/app/page/location/service/location.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { Location } from 'src/app/model/Location'
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { Driver } from 'src/app/model/Driver';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { DatePipe } from '@angular/common';
import { Observable, forkJoin } from 'rxjs';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';

@Component({
  selector: 'app-update-domestic-shipment-for-summary',
  templateUrl: './update-domestic-shipment-for-summary.component.html',
  styleUrls: ['./update-domestic-shipment-for-summary.component.scss'],
  providers:[MessageService,DatePipe]
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
    attachments: null,
    arrivalTime: null,
    departureTime: null,
    preAlertNumber: undefined
  };

  location!:Location[];
  selectedLocation!:Location;
  selectedOriginLocation!:Location;
  selectedDestinationLocation!:Location;
  drivers!:Driver[]


  originFacility!:originFacility[];
  selectedOriginFacility!:originFacility;
  selectedDestinationFacility!:originFacility;

  vehicleTypes!:VehicleType[];
  selectedVehicleTypes!:VehicleType;
  selectedDriver!:Driver|null|undefined;


  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));

  shipmentStatus!:ShipmentStatus[];
  selectedShipmentStatus!:ShipmentStatus;

  domesticShipmentId:any;

  constructor(private locationService: LocationService,
    private vehicleTypeService:VehicleTypeService,
    private shipmentStatusService:ShipmentStatusService,
    private domesticShipmentService:DomesticShippingService,
    private driverService:DriverService,
    private router:Router,
    private messageService: MessageService,
    private route:ActivatedRoute,
    private datePipe:DatePipe) { }
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


    this.items = [{ label: 'Domestic Summary',routerLink:'/domestic-summary'},{ label: 'Edit Domestic Shipment'}];
 
    const locations$: Observable<Location[]> = this.locationService.getAllLocation();
    const driver$: Observable<PaginatedResponse<Driver>> =this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> =this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ShipmentStatus[]> = this.shipmentStatusService.getALLShipmentStatus();

    forkJoin([locations$, driver$, vehicleType$, shipmentStatus$]).subscribe(
      ([locationsResponse,driverResponse,vehicleTypeResponse,shipmentStatusResponse]) => {
        // Access responses here
        this.location=locationsResponse.filter(el => el.status); 
       
        this.drivers=driverResponse.content.filter((el:Driver)=>el.status); 
        this.vehicleTypes=vehicleTypeResponse
        this.shipmentStatus=shipmentStatusResponse
  
     
        this.domesticShipmentById(this.domesticShipmentId);
      }
    );

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

  getAllDriver(){
    this.driverService.getAllDriver().subscribe((res:PaginatedResponse<Driver>)=>{
  
     this.drivers=res.content.filter((el:Driver)=>el.status);  
   
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
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
      res.etd=res.etd?new Date(res.etd):null;
      res.eta=res.eta?new Date(res.eta):null;
      res.atd=res.atd?new Date(res.atd):null;
      res.ata=res.ata?new Date(res.ata):null;
      this.selectedDriver=this.drivers.find((el:Driver)=>{return (el.name==res.driverName)&&(el.contactNumber==res.driverContact)&&(el.referenceNumber==res.referenceNumber)})

      
      this.domesticShipment=res;
    
      

    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }         
    })
   }

   driverData(){
    this.domesticShipment.driverName=this.selectedDriver?.name;
    this.domesticShipment.driverContact=this.selectedDriver?.contactNumber;
    this.domesticShipment.referenceNumber=this.selectedDriver?.referenceNumber;
   }

   updateDomesticShipment(domesticShipment:DomesticShipment){
      this.domesticShipmentService.updateDomesticShipment(this.domesticShipmentId,domesticShipment).subscribe((res:DomesticShipment)=>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Shipment Updated Successfully' });
        
        setTimeout(() => {
          this.router.navigate(['/domestic-summary']);
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
    this.domesticShipment.etd=this.datePipe.transform(this.domesticShipment.etd,'yyyy-MM-dd')
    this.domesticShipment.eta=this.datePipe.transform(this.domesticShipment.eta,'yyyy-MM-dd')
    this.domesticShipment.atd=this.datePipe.transform(this.domesticShipment.atd,'yyyy-MM-dd')
    this.domesticShipment.ata=this.datePipe.transform(this.domesticShipment.ata,'yyyy-MM-dd')
    this.updateDomesticShipment(this.domesticShipment);
   }






}


interface originFacility{
  originFacility:string
}
