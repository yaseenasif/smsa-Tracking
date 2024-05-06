import { ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { VehicleType } from 'src/app/model/VehicleType';
import { LocationService } from 'src/app/page/location/service/location.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { VehicleTypeService } from 'src/app/page/vehicle-type/service/vehicle-type.service';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { Location } from 'src/app/model/Location'
import { NumberOfPallets } from 'src/app/model/NumberOfPallets';
import { DriverService } from 'src/app/page/driver/service/driver.service';
import { Driver } from 'src/app/model/Driver';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { DatePipe } from '@angular/common';
import { Observable, catchError, forkJoin } from 'rxjs';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/page/user/service/user.service';
import { Routes } from 'src/app/model/ShipmentRoutes';


@Component({
  selector: 'app-update-domestic-shipping',
  templateUrl: './update-domestic-shipping.component.html',
  styleUrls: ['./update-domestic-shipping.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateDomesticShippingComponent {
  items: MenuItem[] | undefined;
  defaultDate:Date=new Date(this.datePipe.transform((new Date()).setHours(0, 0, 0, 0),'EEE MMM dd yyyy HH:mm:ss \'GMT\'ZZ (z)')!)
  domesticRoute:Routes={
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
    durationLimit: null,
    remarks: undefined
  }
  domesticShipment: DomesticShipment = {
    originFacility: undefined,
    originLocation: undefined,
    refrigeratedTruck: false,
    destinationFacility: undefined,
    destinationLocation: undefined,
    routeNumber: null,
    numberOfShipments: null,
    weight: null,
    // etd: null,
    // eta: null,
    atd: null,
    driverName: null,
    driverContact: null,
    referenceNumber: null,
    vehicleType: null,
    numberOfPallets: null,
    numberOfBags: null,
    vehicleNumber: null,
    tagNumber: [],
    // sealNumber: null,
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
    preAlertNumber: null,
    transitTimeTaken: null,
    preAlertType: null,
    originCountry: undefined,
    destinationCountry: undefined,
    numberOfBoxes: undefined,
    routeNumberId: null,
    damage: null,
    damageAwbs: null,
    numberOfPalletsReceived: null,
    numberOfBagsReceived: null,
    trip: null
  };


  domesticCountryList!:(Country | null | undefined)[]
  selectedCountry!:string;
  originCountry!: Country[];
  destinationCountry!:Country[];
  orgLocation: Location[]|undefined;
  desLocation: Location[]|undefined;
  originFacility!:(Facility|null|undefined)[]|undefined;
  destinationFacility!: (Facility|null|undefined)[]|undefined;
  user!:User;

  minDate: Date = new Date();
  routes: any = [];
  location!: Location[];
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;
  drivers!: Driver[]

  vehicleTypes!: VehicleType[];
  selectedVehicleTypes!: VehicleType;
  selectedDriver!: Driver | null | undefined;


  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));

  shipmentStatus!: ProductField | null | undefined;
  selectedShipmentStatus!: ProductField;

  domesticShipmentId: any;
  showDropDown: boolean = false;
  domesticShipmentType!: string;
  tripSwitch=false;

  constructor(private locationService: LocationService,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ProductFieldServiceService,
    private domesticShipmentService: DomesticShippingService,
    private driverService: DriverService,
    private router: Router,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private userService:UserService,
    private cdr: ChangeDetectorRef,
    private datePipe: DatePipe) { }
  // name!: string;
  // checked!: boolean;
  // size = 100000
  // uploadedFiles: any[] = [];

  flag=false;
  dashAfterThree(){
    let charToAdd="-";
    if(this.domesticShipment.preAlertNumber!.length===3){
    this.flag=true;
    }
    if(this.domesticShipment.preAlertNumber!.length===4&&this.flag){
      this.domesticShipment.preAlertNumber=this.domesticShipment.preAlertNumber!.slice(0, 3) + charToAdd + this.domesticShipment.preAlertNumber!.slice(3);
      this.flag=false;
    }
  }

  // onUpload(event: any) {

  // }

  // onUpload1(event: any) {
  //   for (let file of event.files) {
  //     this.uploadedFiles.push(file);
  //   }
  // }

  getDomesticRoute() {
    this.showDropDown = true;
    this.routes = []

    if (this.domesticShipment.originLocation !== null && this.domesticShipment.destinationLocation !== null) {
      this.domesticShipmentService.getDomesticRoute(this.domesticShipment.originLocation!, this.domesticShipment.destinationLocation!).subscribe((res: any) => {
        this.routes = res;

      }, (error: any) => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }

  ngOnInit(): void {
    this.getLoggedInUser(); 
    this.getAllLocations()
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;

    this.domesticShipmentType = this.route.snapshot.paramMap.get('type')!;
   if(this.domesticShipmentType=="/from-list"){
    this.items = [{ label: 'Domestic Outbound', routerLink: '/domestic-shipping' }, { label: 'Edit Domestic Outbound' }];
   }else{
    this.items = [{ label: 'Domestic Inbound', routerLink: '/domestic-summary' }, { label: 'Edit Domestic Inbound' }];
   }
    const locations$: Observable<Location[]> = this.locationService.getAllLocationForDomestic();
    const driver$: Observable<PaginatedResponse<Driver>> = this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> = this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ProductField> = this.getAllShipmentStatus();
    const LoggedInUser$: Observable<User> =this.userService.getLoggedInUser();

    forkJoin([locations$, driver$, vehicleType$, shipmentStatus$,LoggedInUser$]).subscribe(
      ([locationsResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse,loggedInUserResponse]) => {
        // Access responses here
        this.location = locationsResponse.filter(el => el.status);
        this.user=loggedInUserResponse;
        this.drivers = driverResponse.content.filter((el: Driver) => el.status);
        this.vehicleTypes = vehicleTypeResponse
        this.shipmentStatus = shipmentStatusResponse
     
        this.domesticShipmentById(this.domesticShipmentId);
       
        
      }
    );




  }

 
  getAllDriver() {
    this.driverService.getAllDriver().subscribe((res: PaginatedResponse<Driver>) => {

      this.drivers = res.content.filter((el: Driver) => el.status);

    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllLocations() {
    this.locationService.getAllLocationForDomestic().subscribe((res: Location[]) => {

    this.domesticCountryList=res.map((location:Location)=>{
    return location.country
     }).filter((obj, index, arr) =>
    index === arr.findIndex((item:Country|null|undefined) => item!.id === obj!.id));
  
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }


  getRouteByRouteNumber(routeNumber: string) {
    this.domesticShipmentService.getRouteByRouteNumber(routeNumber).subscribe((res: any) => {
      
      this.routes.push(res);
      this.getRouteLabel();

    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  getAllVehicleType() {
    this.vehicleTypeService.getALLVehicleType().subscribe((res: VehicleType[]) => {
      this.vehicleTypes = res;
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  // getAllShipmentStatus() {
  //   this.shipmentStatusService.getProductFieldByName("Origin_Of_Domestic").subscribe((res: ProductField) => {
  //     this.shipmentStatus = res;
  //   }, error => {
  //     debugger
  //     if (error.error.body) {
  //       this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //     } else {
  //       this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
  //     }
  //   })
  // }

  getAllShipmentStatus(): Observable<ProductField> {
    return this.shipmentStatusService.getProductFieldByName("Origin_Of_Domestic").pipe(
      catchError(error => {
        if (error.error.body) {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
        }
        throw error;
      })
    );
  }

  domesticShipmentById(id: number) {
    this.domesticShipmentService.getDomesticShipmentById(id).subscribe((res: DomesticShipment) => {
      // res.etd = res.etd ? new Date(res.etd) : null;
      // res.eta = res.eta ? new Date(res.eta) : null;
      res.atd = res.atd ? new Date(res.atd) : null;
      res.ata = res.ata ? new Date(res.ata) : null;
      if(typeof res.tagNumber === 'string'){
      res.tagNumber=res.tagNumber!.split(",");
      }
      this.domesticShipment = res;

      this.selectedDriver = this.drivers.find((el: Driver) => { return (el.name == res.driverName) && (el.contactNumber == res.driverContact)})
      this.originFacility = this.user.domesticOriginLocations
  ?.filter((el) => el.country?.name === this.domesticShipment.originCountry )
  .map(el => el.facility);
  this.originFacility=this.originFacility?.filter((obj, index, self) =>
  index === self.findIndex((o) => o!.id === obj!.id)
  );
  this.destinationFacility=this.user.domesticDestinationLocations
  ?.filter((el) => el.country?.name === this.domesticShipment.destinationCountry )
  .map(el => el.facility);
  this.destinationFacility=this.destinationFacility?.filter((obj, index, self) =>
  index === self.findIndex((o) => o!.id === obj!.id)
  );
      this.onDesFacilityChange()
      this.onOrgFacilityChange()

   
      // originCountry & destinationCountry list data start

      // this.originCountry=[];
      // this.destinationCountry=[];
      // this.user.domesticOriginLocations?.forEach((el)=>{
      //   debugger
      //   return this.originCountry.push(el.facility?.country!);
      // })
      // this.originCountry = this.originCountry.filter((obj, index, arr) =>
      // index === arr.findIndex((item:Country) => item.id === obj.id)
      // );
      

      // this.user.domesticDestinationLocations?.forEach((el)=>{
      //   return this.destinationCountry.push(el.facility?.country!);
      // })
      // this.destinationCountry = this.destinationCountry.filter((obj, index, arr) =>
      // index === arr.findIndex((item:Country) => item.id === obj.id)
      // );
      // originCountry & destinationCountry list data end
     
   

      // originFacility & destinationFacility list data start

      // this.originFacility=[]
      // this.destinationFacility=[]
  
      // let orgFacility=this.user.domesticOriginLocations!.filter(
      //   (location, index, self) =>
      //     location?.facility?.country?.name == res.originCountry &&
      //     index ===
      //       self.findIndex(
      //         (l) =>
      //           l.facility!.id === location.facility!.id
      //       )
      // );
      // let desFacility=this.user.domesticDestinationLocations!.filter(
      //   (location, index, self) =>
      //     location?.facility?.country?.name == res.originCountry &&
      //     index ===
      //       self.findIndex(
      //         (l) =>
      //           l.facility!.id === location.facility!.id
      //       )
      // );
      // let orgFacility= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === country));
      // let desFacility= this.user.domesticDestinationLocations?.filter((obj => obj.facility?.country?.name === country));
      // orgFacility?.forEach((el)=>{
      //  return this.originFacility.push(el?.facility!);
      // })
      // desFacility?.forEach((el)=>{
      //  return this.destinationFacility.push(el?.facility!);
      // })
   // originFacility & destinationFacility list data end
  
  //  this.domesticShipment.originCountry=res.originCountry;
  //  this.domesticShipment.destinationCountry=res.destinationCountry;
      // debugger
      console.log(this.domesticShipment);
      
      

  
     


    
      // this.onOrgFacilityChange(this.domesticShipment.originFacility!);
      // this.onDesFacilityChange(this.domesticShipment.destinationFacility!);

      
      // this.getDomesticRoute();

      this.domesticShipmentService.getRouteByRouteNumber(this.domesticShipment.routeNumber!).subscribe((res: any) => {
      res.find((el:Routes)=>el.id==this.domesticShipment.routeNumberId)
      }, (error: any) => {
        if (error.error.body) {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
        }
      })
      this.getRouteByRouteNumber(this.domesticShipment.routeNumber!);


    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  driverData() {
    this.domesticShipment.driverName = this.selectedDriver?.name;
    this.domesticShipment.driverContact = this.selectedDriver?.contactNumber;
  }

  updateDomesticShipment(domesticShipment: DomesticShipment) {
    let orgLocationId=this.user.domesticOriginLocations?.find((el)=>{return el.country?.name == this.domesticShipment.originCountry && el.facility?.name==this.domesticShipment.originFacility && el.locationName==this.domesticShipment.originLocation})!.id;
    let desLocationId=this.user.domesticDestinationLocations?.find((el)=>{return el.country?.name == this.domesticShipment.destinationCountry && el.facility?.name==this.domesticShipment.destinationFacility && el.locationName==this.domesticShipment.destinationLocation})!.id;
    this.domesticShipmentService.updateDomesticShipment(this.domesticShipmentId,orgLocationId!,desLocationId!, domesticShipment).subscribe((res: DomesticShipment) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Outbound Updated Successfully' });

      setTimeout(() => {
        this.router.navigate(['/domestic-shipping']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
      // this.domesticShipment.etd = this.domesticShipment.etd ? new Date( this.domesticShipment.etd) : null;
      // this.domesticShipment.eta =  this.domesticShipment.eta ? new Date( this.domesticShipment.eta) : null;
      this.domesticShipment.atd =  this.domesticShipment.atd ? new Date( this.domesticShipment.atd) : null;
      this.domesticShipment.ata =  this.domesticShipment.ata ? new Date( this.domesticShipment.ata) : null;
    })
  }

  onSubmit() {
    if(Array.isArray(this.domesticShipment.tagNumber)){
      this.domesticShipment.tagNumber=this.domesticShipment.tagNumber!.join(',');
    }
    // this.domesticShipment.etd = this.datePipe.transform(this.domesticShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    // this.domesticShipment.eta = this.datePipe.transform(this.domesticShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.atd = this.datePipe.transform(this.domesticShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.ata = this.datePipe.transform(this.domesticShipment.ata, 'yyyy-MM-ddTHH:mm:ss')
    this.updateDomesticShipment(this.domesticShipment);
  }

  // onOrgCountryChange(country:string){

  //   let found= this.destinationCountry.find(obj => obj.name === country)
  //   if(found){
  //   this.domesticShipment.destinationCountry=country;
  //   this.originFacility=[]
  //   this.destinationFacility=[]

  //   let orgFacility=this.user.domesticOriginLocations!.filter(
  //     (location, index, self) =>
  //       location?.facility?.country?.name == country &&
  //       index ===
  //         self.findIndex(
  //           (l) =>
  //             l.facility!.id === location.facility!.id
  //         )
  //   );
  //   let desFacility=this.user.domesticDestinationLocations!.filter(
  //     (location, index, self) =>
  //       location?.facility?.country?.name == country &&
  //       index ===
  //         self.findIndex(
  //           (l) =>
  //             l.facility!.id === location.facility!.id
  //         )
  //   );
  //   // let orgFacility= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === country));
  //   // let desFacility= this.user.domesticDestinationLocations?.filter((obj => obj.facility?.country?.name === country));
  //   orgFacility?.forEach((el)=>{
  //    return this.originFacility.push(el?.facility!);
  //   })
  //   desFacility?.forEach((el)=>{
  //    return this.destinationFacility.push(el?.facility!);
  //   })
  //   }
  //   else{
  //    this.domesticShipment.originCountry=this.domesticShipment.destinationCountry;
  //    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User not have country:"'+country+'" in destination country' });
  //   }
   
  //  }
 
  //  onDesCountryChange(country:string){
  //  debugger
  //    let found= this.originCountry.find(obj => obj.name === country)
  //    if(found){
  //    this.domesticShipment.originCountry=country;
  //    this.originFacility=[]
  //    this.destinationFacility=[]
    
  //    let orgFacility=this.user.domesticOriginLocations!.filter(
  //     (location, index, self) =>
  //       location?.facility?.country?.name == country &&
  //       index ===
  //         self.findIndex(
  //           (l) =>
  //             l.facility!.id === location.facility!.id
  //         )
  //   );
  //   let desFacility=this.user.domesticDestinationLocations!.filter(
  //     (location, index, self) =>
  //       location?.facility?.country?.name == country &&
  //       index ===
  //         self.findIndex(
  //           (l) =>
  //             l.facility!.id === location.facility!.id
  //         )
  //   );
  //    orgFacility?.forEach((el)=>{
  //     return this.originFacility.push(el?.facility!);
  //    })
  //    desFacility?.forEach((el)=>{
  //     return this.destinationFacility.push(el?.facility!);
  //    })
  //   }else{
  //    this.domesticShipment.destinationCountry=this.domesticShipment.originCountry
  //    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User not have country:"'+country+'" in origen country' });
  //   }
  //  }
 
  //  onOrgFacilityChange(facility:string){
  //    this.orgLocation= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === this.domesticShipment.originCountry && obj.facility?.name === facility));
  //  }
  //  onDesFacilityChange(facility:string){
  //    this.desLocation= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === this.domesticShipment.destinationCountry && obj.facility?.name === facility));
  //  }
   
   getLoggedInUser(){
    this.userService.getLoggedInUser().subscribe((res: User) => {
      this.user=res;
   
      
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
}

searchLocationByCountry(country:string){
  //  this.orgLocation=this.user.domesticOriginLocations?.filter((element)=> element.country?.name==country);
  //  this.desLocation=this.user.domesticDestinationLocations?.filter((element)=> element.country?.name==country);
  //  this.domesticShipment.originCountry=null;
  //  this.domesticShipment.originFacility=null;
  //  this.domesticShipment.destinationCountry=null;
  //  this.domesticShipment.destinationFacility=null;
  this.domesticShipment.originCountry=this.user.domesticOriginLocations?.find((el)=>el.country?.name==country)?.country?.name
  this.domesticShipment.destinationCountry=this.user.domesticDestinationLocations?.find((el)=>el.country?.name==country)?.country?.name

  this.originFacility = this.user.domesticOriginLocations
  ?.filter((el) => el.country?.name === this.domesticShipment.originCountry )
  .map(el => el.facility);
  this.originFacility=this.originFacility?.filter((obj, index, self) =>
  index === self.findIndex((o) => o!.id === obj!.id)
  );
  this.destinationFacility=this.user.domesticDestinationLocations
  ?.filter((el) => el.country?.name === this.domesticShipment.destinationCountry )
  .map(el => el.facility);
  this.destinationFacility=this.destinationFacility?.filter((obj, index, self) =>
  index === self.findIndex((o) => o!.id === obj!.id)
  );

 this.orgLocation=[];
 this.desLocation=[];

}

onOrgFacilityChange(){
 this.orgLocation=this.user.domesticOriginLocations?.filter((el)=> el.country?.name==this.domesticShipment.originCountry && el.facility?.name==this.domesticShipment.originFacility)
}
onDesFacilityChange(){
  this.desLocation=this.user.domesticDestinationLocations?.filter((el)=> el.country?.name==this.domesticShipment.destinationCountry && el.facility?.name==this.domesticShipment.destinationFacility)
}

onRouteChange(route:Routes){

  if(route.route?.toLowerCase().includes("adhoc")){
    this.tripSwitch=true;
   }else{
    this.tripSwitch=false;
    this.domesticShipment.trip=null;
   }

  this.domesticShipment.routeNumber=route.route;
  this.domesticShipment.routeNumberId=route.id;
 }

 getRouteLabel(){
  this.domesticRoute=this.routes.find((el:Routes)=> el.route==this.domesticShipment.routeNumber);
  if(this.domesticRoute.route?.toLowerCase().includes("adhoc")){
    this.tripSwitch=true;
   }else{
    this.tripSwitch=false;
   }
 }



 


}







