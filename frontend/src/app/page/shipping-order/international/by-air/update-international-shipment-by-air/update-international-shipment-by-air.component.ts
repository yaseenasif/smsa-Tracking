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
// import { LocationPort } from 'src/app/model/LocationPort';
import { Driver } from 'src/app/model/Driver';
import { VehicleType } from 'src/app/model/VehicleType';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { Mode } from 'src/app/model/Mode';
import { ShipmentMode } from 'src/app/model/ShipmentMode';
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { Location } from '../../../../../model/Location'
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { Observable, catchError, forkJoin } from 'rxjs';
import { DatePipe } from '@angular/common';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';

@Component({
  selector: 'app-update-international-shipment-by-air',
  templateUrl: './update-international-shipment-by-air.component.html',
  styleUrls: ['./update-international-shipment-by-air.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateInternationalShipmentByAirComponent {
  items: MenuItem[] | undefined;
  iSID!: number;
  defaultDate:Date=new Date(this.datePipe.transform((new Date()).setHours(0, 0, 0, 0),'EEE MMM dd yyyy HH:mm:ss \'GMT\'ZZ (z)')!)
  routes: any;
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
    status: null,
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
    numberOfBoxes: undefined
  }
  location!: Location[];
  // originPorts!: LocationPort[];
  // destinationPorts!: LocationPort[];
  drivers!: Driver[]
  vehicleTypes!: VehicleType[]
  shipmentStatus!: ProductField | null | undefined;
  selectedDriver!: Driver | null | undefined;
  modeOptions: { options: string }[] = Object.values(Mode).map(el => ({ options: el }));
  shipmentMode: { options: string }[] = Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));
  showDropDown: boolean = false;
  selectedLocation!: Location;
  minDate: Date = new Date();
  carrier:ProductField|undefined|null;
  iST!: string;
  originCountry!: Country[];
  destinationCountry!:Country[];
  orgLocation: Location[]|undefined;
  desLocation: Location[]|undefined;
  originFacility!: (Facility|null|undefined)[]|undefined;
  destinationFacility!: (Facility|null|undefined)[]|undefined;
  user!:User;

  constructor(private router: Router,
    private userService:UserService,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private locationService: LocationService,
    private locationPortService: LocationPortService,
    private driverService: DriverService,
    private route: ActivatedRoute,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ProductFieldServiceService,
    private datePipe: DatePipe) { }




  ngOnInit(): void {
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.iST = this.route.snapshot.paramMap.get('type')!;
    if(this.iST=="/from-list"){
      this.items = [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Air', routerLink: '/international-shipment-by-air' }, { label: 'Edit International Outbound By Air' }];
    }else{
     this.items = [{ label: 'International Inbound By Air', routerLink: '/international-summary-by-air' }, { label: 'Edit International Inbound By Air' }];
    }

    
    const locations$: Observable<Location[]> = this.locationService.getAllLocationForInternational();
    // const locationPort$: Observable<LocationPort[]> =this.locationPortService.getAllLocationPort();
    const driver$: Observable<PaginatedResponse<Driver>> = this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> = this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ProductField> = this.getAllShipmentStatus();
    const shipmentCarrier$: Observable<ProductField> = this.getAllShipmentCarrier();
    const LoggedInUser$: Observable<User> =this.userService.getLoggedInUser();


    forkJoin([locations$, driver$, vehicleType$, shipmentStatus$, shipmentCarrier$,LoggedInUser$]).subscribe(
      ([locationsResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse, shipmentCarrierResponse,userResponse]) => {
        // Access responses here
        this.location = locationsResponse.filter(el => el.status);
        // this.locationPort=locationPortResponse.filter(el => el.status);
        this.drivers = driverResponse.content.filter((el: Driver) => el.status);
        this.vehicleTypes = vehicleTypeResponse
        this.shipmentStatus = shipmentStatusResponse
        this.carrier = shipmentCarrierResponse
        this.user=userResponse;
       
        // Now that you have the responses, you can proceed with the next steps
        this.getInternationalShipmentById(this.iSID);
      }
    );
  }

  // getLocationPortByLocationForOrigin() {
  //   this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.originCountry!).subscribe((res) => {
  //     this.originPorts = res;
  //   }, (error) => {
  //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //   })
  // }
  // getLocationPortByLocationForDestination() {
  //   this.internationalShippingService.getLocationPortByLocation(this.internationalShipment.destinationCountry!).subscribe((res) => {
  //     this.destinationPorts = res;
  //   }, (error) => {
  //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //   })
  // }

  onSubmit() {
    debugger
    this.internationalShipment.etd = this.datePipe.transform(this.internationalShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.eta = this.datePipe.transform(this.internationalShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.atd = this.datePipe.transform(this.internationalShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.ata = this.datePipe.transform(this.internationalShipment.ata, 'yyyy-MM-ddTHH:mm:ss')

    this.internationalShippingService.updateInternationalShipmentById(this.iSID, this.internationalShipment).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is updated on id' + res.id });
      setTimeout(() => {
        this.router.navigate(['/international-shipment-by-air']);
      }, 800);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      this.internationalShipment.etd = this.internationalShipment.etd ? new Date(this.internationalShipment.etd) : null;
      this.internationalShipment.eta = this.internationalShipment.eta ? new Date(this.internationalShipment.eta) : null;
      this.internationalShipment.atd = this.internationalShipment.atd ? new Date(this.internationalShipment.atd) : null;
      this.internationalShipment.ata = this.internationalShipment.ata ? new Date(this.internationalShipment.ata) : null;
    })
  }

  getInternationalShipmentById(id: number) {

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res: InternationalShipment) => {

      res.etd = res.etd ? new Date(res.etd) : null;
      res.eta = res.eta ? new Date(res.eta) : null;
      res.atd = res.atd ? new Date(res.atd) : null;
      res.ata = res.ata ? new Date(res.ata) : null;

      this.selectedDriver = this.drivers.find(el => (el.name == res.driverName) && (el.contactNumber == res.driverContact))
      this.internationalShipment = res;

      this.originCountry = [];
      this.destinationCountry = [];
      this.user.internationalAirOriginLocation?.forEach((el) => {
        return this.originCountry.push(el.country!);
      });
      this.originCountry = this.originCountry.filter(
        (obj, index, arr) =>
          index === arr.findIndex((item: Country) => item.id === obj.id)
      );

      this.user.internationalAirDestinationLocation?.forEach((el) => {
        return this.destinationCountry.push(el.country!);
      });
      this.destinationCountry = this.destinationCountry.filter(
        (obj, index, arr) =>
          index === arr.findIndex((item: Country) => item.id === obj.id)
      );

      this.originFacility = [];
      this.originFacility = this.user.internationalAirOriginLocation
    ?.filter((el) => el.country?.name === this.internationalShipment.originCountry )
    .map(el => el.facility);
    this.originFacility=this.originFacility?.filter((obj, index, self) =>
    index === self.findIndex((o) => o!.id === obj!.id)
    );

    this.destinationFacility = [];
    this.destinationFacility=this.user.internationalAirDestinationLocation
    ?.filter((el) => el.country?.name === this.internationalShipment.destinationCountry )
    .map(el => el.facility);
    this.destinationFacility=this.destinationFacility?.filter((obj, index, self) =>
    index === self.findIndex((o) => o!.id === obj!.id)
    );  


      this.onDesFacilityChange();
      this.onOrgFacilityChange();

      // this.originCountry=[];
      // this.destinationCountry=[];
      // this.user.internationalAirOriginLocation?.forEach((el)=>{
      //   return this.originCountry.push(el.facility?.country!);
      // })
      // this.originCountry = this.originCountry.filter((obj, index, arr) =>
      // index === arr.findIndex((item:Country) => item.id === obj.id)
      // );
      

      // this.user.internationalAirDestinationLocation?.forEach((el)=>{
      //   return this.destinationCountry.push(el.facility?.country!);
      // })
      // this.destinationCountry = this.destinationCountry.filter((obj, index, arr) =>
      // index === arr.findIndex((item:Country) => item.id === obj.id)
      // );
      
      // this.onOrgCountryChange(this.internationalShipment.originCountry!)
      // this.onDesCountryChange(this.internationalShipment.destinationCountry!)
      // this.onOrgFacilityChange(this.internationalShipment.originFacility!)
      // this.onDesFacilityChange(this.internationalShipment.destinationFacility!)

      // this.getLocationPortByLocationForOrigin();
      // this.getLocationPortByLocationForDestination();



       this.getInternationalRouteForAir()
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllShipmentCarrier(): Observable<ProductField>  {
    return this.shipmentStatusService.getProductFieldByName("Carrier").pipe(
      catchError(
        error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
          throw error
        })
    );
  }

  getAllLocations() {
    this.locationService.getAllLocationForInternational().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);


    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getInternationalRouteForAir() {
    this.showDropDown = true;
    this.routes = []
    if (this.internationalShipment.originLocation !== null && this.internationalShipment.destinationLocation !== null && this.internationalShipment.trip !== null) {
      this.internationalShippingService.getInternationalRouteForAir(this.internationalShipment.originLocation!, this.internationalShipment.destinationLocation!, this.internationalShipment.trip!).subscribe((res: any) => {
        this.routes = res;

      }, (error: any) => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    } else {
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

  getAllShipmentStatus(): Observable<ProductField> {
    return this.shipmentStatusService.getProductFieldByName("Origin_Of_International_By_Air").pipe(
      catchError(
        error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
          throw error
        })
    );
  }

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


  // onOrgCountryChange(country:string){
  //   this.originFacility=[]
  //   let orgFacility=this.user.internationalAirOriginLocation!.filter(
  //    (location, index, self) =>
  //      location?.facility?.country?.name == country &&
  //      index ===
  //        self.findIndex(
  //          (l) =>
  //            l.facility!.id === location.facility!.id
  //        )
  //  );
   
  //   orgFacility?.forEach((el)=>{
  //    return this.originFacility.push(el?.facility!);
  //   })

  //   this.destinationCountry = [];
  //   this.user.internationalAirDestinationLocation?.forEach((el) => {
  //     return this.destinationCountry.push(el.facility?.country!);
  //   });
  //   this.destinationCountry = this.destinationCountry.filter(
  //     (obj, index, arr) =>
  //       index === arr.findIndex((item: Country) => item.id === obj.id)&&obj.name!=country
  //   );
     
  //  }
 
  //  onDesCountryChange(country:string){
  //    this.destinationFacility=[]
  //    let desFacility=this.user.internationalAirDestinationLocation!.filter(
  //      (location, index, self) =>
  //        location?.facility?.country?.name == country &&
  //        index ===
  //          self.findIndex(
  //            (l) =>
  //              l.facility!.id === location.facility!.id
  //          )
  //    );
  //     desFacility?.forEach((el)=>{
  //      return this.destinationFacility.push(el?.facility!);
  //     })

  //     this.originCountry = [];
  //     this.user.internationalAirOriginLocation?.forEach((el) => {
  //       return this.originCountry.push(el.facility?.country!);
  //     });
  //     this.originCountry = this.originCountry.filter(
  //       (obj, index, arr) =>
  //         index === arr.findIndex((item: Country) => item.id === obj.id)&&obj.name!=country
  //     );
  //  }

  // onOrgFacilityChange(facility:string){
  //   this.orgLocation= this.user.internationalAirOriginLocation?.filter((obj => obj.facility?.country?.name === this.internationalShipment.originCountry && obj.facility?.name === facility));
  // }
  // onDesFacilityChange(facility:string){
  //   this.desLocation= this.user.internationalAirDestinationLocation?.filter((obj => obj.facility?.country?.name === this.internationalShipment.destinationCountry && obj.facility?.name === facility));
  // }

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

