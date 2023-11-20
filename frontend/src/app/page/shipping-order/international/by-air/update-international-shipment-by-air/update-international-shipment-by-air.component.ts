import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';
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
import {Location} from '../../../../../model/Location'
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { Observable, forkJoin } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-update-international-shipment-by-air',
  templateUrl: './update-international-shipment-by-air.component.html',
  styleUrls: ['./update-international-shipment-by-air.component.scss'],
  providers:[MessageService,DatePipe]
})
export class UpdateInternationalShipmentByAirComponent {
  items: MenuItem[] | undefined ;
  iSID!:number;
  routes:any;
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
  originPorts!:LocationPort[];
  destinationPorts!:LocationPort[];
  drivers!:Driver[]
  vehicleTypes!:VehicleType[]
  shipmentStatus!:ShipmentStatus[];
  selectedDriver!:Driver|null|undefined;
  modeOptions:{ options: string }[] =Object.values(Mode).map(el => ({ options: el }));
  shipmentMode:{ options: string }[] =Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));
  showDropDown:boolean = false;
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
    private datePipe:DatePipe) { }




  ngOnInit(): void {
    this.iSID=+this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'Edit International Shipment By Air'}];
    const locations$: Observable<Location[]> = this.locationService.getAllLocationForInternational();
    // const locationPort$: Observable<LocationPort[]> =this.locationPortService.getAllLocationPort();
    const driver$: Observable<PaginatedResponse<Driver>> =this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> =this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ShipmentStatus[]> = this.shipmentStatusService.getALLShipmentStatus();

    forkJoin([locations$,  driver$, vehicleType$, shipmentStatus$]).subscribe(
      ([locationsResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse]) => {
        // Access responses here
        this.location=locationsResponse.filter(el => el.status);
        // this.locationPort=locationPortResponse.filter(el => el.status);
        this.drivers=driverResponse.content.filter((el:Driver)=>el.status);
        this.vehicleTypes=vehicleTypeResponse
        this.shipmentStatus=shipmentStatusResponse
        // Now that you have the responses, you can proceed with the next steps
        this.getInternationalShipmentById(this.iSID);
      }
    );
  }

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

   onSubmit() {
    this.internationalShipment.etd=this.datePipe.transform(this.internationalShipment.etd,'yyyy-MM-dd')
    this.internationalShipment.eta=this.datePipe.transform(this.internationalShipment.eta,'yyyy-MM-dd')
    this.internationalShipment.atd=this.datePipe.transform(this.internationalShipment.atd,'yyyy-MM-dd')
    this.internationalShipment.ata=this.datePipe.transform(this.internationalShipment.ata,'yyyy-MM-dd')
    this.internationalShipment.departureDate=this.datePipe.transform(this.internationalShipment.departureDate,'yyyy-MM-dd')
    this.internationalShipment.arrivalDate=this.datePipe.transform(this.internationalShipment.arrivalDate,'yyyy-MM-dd')
    this.internationalShipment.departureTime=this.datePipe.transform(this.internationalShipment.departureTime,'HH:mm:ss')
    this.internationalShipment.arrivalTime=this.datePipe.transform(this.internationalShipment.arrivalTime,'HH:mm:ss')

    this.internationalShippingService.updateInternationalShipmentById(this.iSID,this.internationalShipment).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/international-shipment-by-air']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'International Shipment is not updated'});
    })
  }

  getInternationalShipmentById(id:number){

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res:InternationalShipment)=>{

     res.etd=res.etd ? new Date(res.etd) : null;
     res.eta=res.eta ? new Date(res.eta) : null;
     res.atd=res.atd ? new Date(res.atd) : null;
     res.ata=res.ata ? new Date(res.ata) : null;
     res.departureDate=res.departureDate ? new Date(res.departureDate) : null;
     res.arrivalDate=res.arrivalDate ? new Date(res.arrivalDate) : null;
     res.departureTime=res.departureTime ? new Date(`1970-01-01 ${res.departureTime}`) : null;
     res.arrivalTime = res.arrivalTime ? new Date(`1970-01-01 ${res.arrivalTime}`) : null;

     this.selectedDriver=this.drivers.find(el=>(el.name==res.driverName)&&(el.contactNumber==res.driverContact)&&(el.referenceNumber==res.referenceNumber))
     this.internationalShipment=res;
     this.getLocationPortByLocationForOrigin();
     this.getLocationPortByLocationForDestination();
    //  this.getInternationalRouteForAir()
    },error=>{
     this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not International Shipment by id'});
    })
  }

  getAllLocations(){
    this.locationService.getAllLocationForInternational().subscribe((res:Location[])=>{
      this.location=res.filter(el => el.status);


    },error=>{
    })
  }

  getInternationalRouteForAir() {
    this.showDropDown = true;
    this.routes=[]
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

  // getAllLocationPort(){
  //   this.locationPortService.getAllLocationPort().subscribe((res:LocationPort[])=>{
  //     this.locationPort=res.filter(el=>el.status)
  //   },error=>{})
  // }
  // getAllLocationPort(){
  //   this.locationPortService.getAllLocationPort().subscribe((res:LocationPort[])=>{
  //     this.locationPort=res.filter(el=>el.status)
  //   },error=>{})
  // }
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

