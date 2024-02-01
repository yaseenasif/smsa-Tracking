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
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';
import { empty } from 'rxjs';
@Component({
  selector: 'app-add-domestic-shipping',
  templateUrl: './add-domestic-shipping.component.html',
  styleUrls: ['./add-domestic-shipping.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddDomesticShippingComponent {
  items: MenuItem[] | undefined;
  routes: any;

  domesticShipment: DomesticShipment = {
    originFacility: null,
    originLocation: null,
    refrigeratedTruck: false,
    destinationFacility: null,
    destinationLocation: null,
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
    tagNumber: null,
    // sealNumber: null,
    status: "Created",
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
    originCountry: null,
    destinationCountry: null
  };

  originCountry!: Country[];
  destinationCountry!:Country[];

  orgLocation: Location[]|undefined;
  desLocation: Location[]|undefined;
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;


  originFacility!: Facility[];
  destinationFacility!: Facility[];
  
  vehicleTypes!: VehicleType[];
  selectedVehicleTypes!: VehicleType;

  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));


  shipmentStatus!: ProductField;
  selectedShipmentStatus!: ProductField;
  minDate: Date = new Date();

  drivers!: Driver[]

  constructor(private locationService: LocationService,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ShipmentStatusService,
    private messageService: MessageService,
    private domesticShipmentService: DomesticShippingService,
    private driverService: DriverService,
    private router: Router,
    private datePipe: DatePipe,
    private productFieldService: ProductFieldServiceService,
    private userService:UserService
  ) { }
  // name!: string;
  // checked!: boolean;
  // size = 100000
  // uploadedFiles: any[] = [];
  fromDate: any;
  selectedDriver: Driver | null = null;
  user!:User;

  flag = false;


  // onUpload(event: any) {

  // }

  // onUpload1(event: any) {
  //   for (let file of event.files) {
  //     this.uploadedFiles.push(file);
  //   }
  // }

  ngOnInit(): void {


    this.items = [{ label: 'Domestic Shipment', routerLink: '/domestic-shipping' }, { label: 'Add Domestic Shipment' }];
    // this.getAllLocations();
    this.getAllVehicleType();
    // this.getAllShipmentStatus();
    this.getAllDriver();
    this.getLoggedInUser(); 
  }

  
  getLoggedInUser(){
      this.userService.getLoggedInUser().subscribe((res: User) => {
        this.user=res;
        this.originCountry=[];
        this.destinationCountry=[];
        res.domesticOriginLocations?.forEach((el)=>{
          return this.originCountry.push(el.facility?.country!);
        })
        this.originCountry = this.originCountry.filter((obj, index, arr) =>
        index === arr.findIndex((item:Country) => item.id === obj.id)
        );
        

        res.domesticDestinationLocations?.forEach((el)=>{
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

  getDomesticRoute() {
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

  // getAllLocations() {
  //   this.locationService.getAllLocationForDomestic().subscribe((res: Location[]) => {
  //     this.location = res.filter(el => el.status);
  //   }, error => {
  //     if (error.error.body) {
  //       this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //     } else {
  //       this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
  //     }
  //   })
  // }

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
  //   this.productFieldService.getProductFieldByName("Auto_Status").subscribe(
  //     (res: ProductField) => {
  //       this.domesticShipment.status = res.productFieldValuesList[0].name;
  //     },
  //     (error) => {
  //       if (error.error.body) {
  //         this.messageService.add({
  //           severity: 'error',
  //           summary: 'Error',
  //           detail: error.error.body,
  //         });
  //       } else {
  //         this.messageService.add({
  //           severity: 'error',
  //           summary: 'Error',
  //           detail: error.error,
  //         });
  //       }
  //     }
  //   );
  // }



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

        // this.domesticShipment.etd = this.domesticShipment.etd ? new Date( this.domesticShipment.etd) : null;
        // this.domesticShipment.eta =  this.domesticShipment.eta ? new Date( this.domesticShipment.eta) : null;
        this.domesticShipment.atd =  this.domesticShipment.atd ? new Date( this.domesticShipment.atd) : null;
        this.domesticShipment.ata =  this.domesticShipment.ata ? new Date( this.domesticShipment.ata) : null;
      })
   }

  getAllDriver() {
    this.driverService.getAllDriver().subscribe((res: PaginatedResponse<Driver>) => {

      this.drivers = res.content.filter((el: Driver) => el.status);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  driverData() {
    this.domesticShipment.driverName = this.selectedDriver?.name;
    this.domesticShipment.driverContact = this.selectedDriver?.contactNumber;
    this.domesticShipment.referenceNumber = this.selectedDriver?.referenceNumber;
  }

  onSubmit() {
    // this.domesticShipment.etd = this.datePipe.transform(this.domesticShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    // this.domesticShipment.eta = this.datePipe.transform(this.domesticShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.atd = this.datePipe.transform(this.domesticShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.ata = this.datePipe.transform(this.domesticShipment.ata, 'yyyy-MM-ddTHH:mm:ss')
    this.addDomesticShipment(this.domesticShipment);
  }


  onOrgCountryChange(country:string){
 
   let found= this.destinationCountry.find(obj => obj.name === country)
   if(found){
   this.domesticShipment.destinationCountry=country;
   this.originFacility=[]
   this.destinationFacility=[]
   let orgFacility=this.user.domesticOriginLocations!.filter(
    (location, index, self) =>
      location?.facility?.country?.name == country &&
      index ===
        self.findIndex(
          (l) =>
            l.facility!.id === location.facility!.id
        )
  );
  let desFacility=this.user.domesticDestinationLocations!.filter(
    (location, index, self) =>
      location?.facility?.country?.name == country &&
      index ===
        self.findIndex(
          (l) =>
            l.facility!.id === location.facility!.id
        )
  );
   orgFacility?.forEach((el)=>{
    return this.originFacility.push(el?.facility!);
   })
   desFacility?.forEach((el)=>{
    return this.destinationFacility.push(el?.facility!);
   })
   }
   else{
    this.domesticShipment.originCountry=this.domesticShipment.destinationCountry;
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User not have country:"'+country+'" in destination country' });
   }
  
  }

  onDesCountryChange(country:string){
  
    let found= this.originCountry.find(obj => obj.name === country)
    if(found){
    this.domesticShipment.originCountry=country;
    this.originFacility=[]
    this.destinationFacility=[]
   
    let orgFacility=this.user.domesticOriginLocations!.filter(
      (location, index, self) =>
        location?.facility?.country?.name == country &&
        index ===
          self.findIndex(
            (l) =>
              l.facility!.id === location.facility!.id
          )
    );
    let desFacility=this.user.domesticDestinationLocations!.filter(
      (location, index, self) =>
        location?.facility?.country?.name == country &&
        index ===
          self.findIndex(
            (l) =>
              l.facility!.id === location.facility!.id
          )
    );
     orgFacility?.forEach((el)=>{
      return this.originFacility.push(el?.facility!);
     })
     desFacility?.forEach((el)=>{
      return this.destinationFacility.push(el?.facility!);
     })
   }else{
    this.domesticShipment.destinationCountry=this.domesticShipment.originCountry
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User not have country:"'+country+'" in origen country' });
   }
  }

  onOrgFacilityChange(facility:string){
    this.orgLocation= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === this.domesticShipment.originCountry && obj.facility?.name === facility));
  }
  onDesFacilityChange(facility:string){
    this.desLocation= this.user.domesticOriginLocations?.filter((obj => obj.facility?.country?.name === this.domesticShipment.destinationCountry && obj.facility?.name === facility));
  }
}






