import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { LocationService } from 'src/app/page/location/service/location.service';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { LocationPort } from 'src/app/model/LocationPort';
import { Driver } from 'src/app/model/Driver';
import { VehicleType } from 'src/app/model/VehicleType';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { Mode } from 'src/app/model/Mode';
import { ShipmentMode } from 'src/app/model/ShipmentMode';
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import {Location} from 'src/app/model/Location'
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { Observable, forkJoin } from 'rxjs';
import { DatePipe } from '@angular/common';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';

@Component({
  selector: 'app-update-international-road-for-summary',
  templateUrl: './update-international-road-for-summary.component.html',
  styleUrls: ['./update-international-road-for-summary.component.scss'],
  providers:[MessageService,DatePipe]
})
export class UpdateInternationalRoadForSummaryComponent {
  items: MenuItem[] | undefined ;
  iSID!:number;
  internationalShipment:InternationalShipment={
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
    status: null,
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
  location!:Location[];
  locationPort!:LocationPort[]
  drivers!:Driver[]
  vehicleTypes!:VehicleType[]
  shipmentStatus!:ShipmentStatus[];
  selectedDriver!:Driver|null|undefined;
  modeOptions:{ options: string }[] =Object.values(Mode).map(el => ({ options: el }));
  shipmentMode:{ options: string }[] =Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));
  
  selectedLocation!:Location;

  constructor(private router:Router,
    private internationalShippingService:InternationalShippingService,
    private messageService:MessageService,
    private locationService:LocationService,
    private locationPortService:LocationPortService,
    private driverService:DriverService,
    private route: ActivatedRoute,
    private vehicleTypeService:VehicleTypeService,
    private shipmentStatusService:ShipmentStatusService,
    private datePipe: DatePipe) { }
    
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
    this.iSID=+this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Summary By Road',routerLink:'/international-summary-by-road'},{ label: 'Edit International Shipment By Road'}];    
   
    const locations$: Observable<Location[]> = this.locationService.getAllLocation();
    const locationPort$: Observable<LocationPort[]> =this.locationPortService.getAllLocationPort();
    const driver$: Observable<PaginatedResponse<Driver>> =this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> =this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ShipmentStatus[]> = this.shipmentStatusService.getALLShipmentStatus();
  
    forkJoin([locations$, locationPort$, driver$, vehicleType$, shipmentStatus$]).subscribe(
      ([locationsResponse, locationPortResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse]) => {
        // Access responses here
        this.location=locationsResponse.filter(el => el.status); 
        this.locationPort=locationPortResponse.filter(el => el.status); 
        this.drivers=driverResponse.content.filter((el:Driver)=>el.status); 
        this.vehicleTypes=vehicleTypeResponse
        this.shipmentStatus=shipmentStatusResponse
  
        // Now that you have the responses, you can proceed with the next steps
        this.getInternationalShipmentById(this.iSID);
      }
    );
  }

   onSubmit() {
   this.internationalShipment.etd=this.datePipe.transform(this.internationalShipment.etd,'yyyy-MM-dd')
   this.internationalShipment.eta=this.datePipe.transform(this.internationalShipment.eta,'yyyy-MM-dd')
   this.internationalShipment.atd=this.datePipe.transform(this.internationalShipment.atd,'yyyy-MM-dd')
   this.internationalShipment.ata=this.datePipe.transform(this.internationalShipment.ata,'yyyy-MM-dd')
    
    this.internationalShippingService.updateInternationalShipmentById(this.iSID,this.internationalShipment).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/international-summary-by-road']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'International Shipment is not updated'});
    })  
  }
  
  getInternationalShipmentById(id:number){
    
    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res:InternationalShipment)=>{
     res.etd=res.etd?new Date(res.etd):null;
     res.eta=res.eta?new Date(res.eta):null;
     res.atd=res.atd?new Date(res.atd):null;
     res.ata=res.ata?new Date(res.ata):null;
     this.selectedDriver=this.drivers.find(el=>(el.name==res.driverName)&&(el.contactNumber==res.driverContact)&&(el.referenceNumber==res.referenceNumber))
     
     this.internationalShipment=res;  
    
     
    },error=>{
     this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not International Shipment by id'});
    })
  }

  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.location=res.filter(el => el.status);   
  
      
    },error=>{
    })
  }

  getAllLocationPort(){
    this.locationPortService.getAllLocationPort().subscribe((res:LocationPort[])=>{
      this.locationPort=res.filter(el=>el.status)
    },error=>{})
  }
  getAllDriver(){
    this.driverService.getAllDriver().subscribe((res:PaginatedResponse<Driver>)=>{
  
     this.drivers=res.content.filter((el:Driver)=>el.status);  
    },error=>{})
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

   driverData(){
    this.internationalShipment.driverName=this.selectedDriver?.name;
    this.internationalShipment.driverContact=this.selectedDriver?.contactNumber;
    this.internationalShipment.referenceNumber=this.selectedDriver?.referenceNumber;
   }
}




