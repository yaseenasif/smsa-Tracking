import { ChangeDetectorRef, Component } from '@angular/core';
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
import { Observable, catchError, forkJoin } from 'rxjs';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { ChipsAddEvent } from 'primeng/chips';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-update-domestic-shipment-for-summary',
  templateUrl: './update-domestic-shipment-for-summary.component.html',
  styleUrls: ['./update-domestic-shipment-for-summary.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateDomesticShipmentForSummaryComponent {
  

  defaultDate:Date=new Date(this.datePipe.transform((new Date()).setHours(0, 0, 0, 0),'EEE MMM dd yyyy HH:mm:ss \'GMT\'ZZ (z)')!)
  items: MenuItem[] | undefined;

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
    preAlertNumber: undefined,
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

  location!: Location[];
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;
  drivers!: Driver[]


  originFacility!: originFacility[];
  selectedOriginFacility!: originFacility;
  selectedDestinationFacility!: originFacility;

  vehicleTypes!: VehicleType[];
  selectedVehicleTypes!: VehicleType;
  selectedDriver!: Driver | null | undefined;


  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));

  shipmentStatus!: ProductField | null | undefined;
  // selectedShipmentStatus!: ProductField;

  domesticShipmentId: any;
  user!: User;
  required!: boolean;

  constructor(private locationService: LocationService,
    private cdr: ChangeDetectorRef,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ProductFieldServiceService,
    private domesticShipmentService: DomesticShippingService,
    private driverService: DriverService,
    private router: Router,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private userService:UserService,
    private datePipe: DatePipe) { }
  name!: string;
  checked!: boolean;
  size = 100000
  uploadedFiles: any[] = [];
  minDate: Date = new Date();

  onPasteOveragesAwbs() {  
    this.domesticShipment.overagesAwbs=this.domesticShipment.overagesAwbs!.match(/[^ ,]+/g)!.join(',')
  }
  onPasteShortagesAwbs() {  
    this.domesticShipment.shortagesAwbs=this.domesticShipment.shortagesAwbs!.match(/[^ ,]+/g)!.join(',')
  }
  onPasteDamageAwbs() {  
    this.domesticShipment.damageAwbs=this.domesticShipment.damageAwbs!.match(/[^ ,]+/g)!.join(',')
  }

  onUpload1(event: any) {
    for (let file of event.files) {
      this.uploadedFiles.push(file);
    }
  }

  ngOnInit(): void {
    this.getLoggedInUser();
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;


    this.items = [{ label: 'Domestic Inbound', routerLink: '/domestic-summary' }, { label: 'Edit Domestic Inbound' }];

    const locations$: Observable<Location[]> = this.locationService.getAllLocation();
    const driver$: Observable<PaginatedResponse<Driver>> = this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> = this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ProductField> = this.getAllShipmentStatus();

    forkJoin([locations$, driver$, vehicleType$, shipmentStatus$]).subscribe(
      ([locationsResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse]) => {
        // Access responses here
        this.location = locationsResponse.filter(el => el.status);

        this.drivers = driverResponse.content.filter((el: Driver) => el.status);
        this.vehicleTypes = vehicleTypeResponse
        this.shipmentStatus = shipmentStatusResponse
        

        this.domesticShipmentById(this.domesticShipmentId);
      }
    );

    this.originFacility = [
      {
        originFacility: "HUB"
      },
      {
        originFacility: "Station"
      },
      {
        originFacility: "Gateway"
      }
    ]


  }

  getAllDriver() {
    this.driverService.getAllDriver().subscribe((res: PaginatedResponse<Driver>) => {

      this.drivers = res.content.filter((el: Driver) => el.status);

    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllLocations() {
    this.locationService.getAllLocation().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);
    }, error => {
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

  getAllShipmentStatus(): Observable<ProductField> {
    return this.shipmentStatusService.getProductFieldByName("Destination_Of_Domestic").pipe(
      catchError(error => {
        if (error.error.body) {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
        }
        throw error
      })
    );
  }

  domesticShipmentById(id: number) {
    this.domesticShipmentService.getDomesticShipmentById(id).subscribe((res: DomesticShipment) => {
      // res.etd = res.etd ? new Date(res.etd) : null;
      // res.eta = res.eta ? new Date(res.eta) : null;
      res.atd = res.atd ? new Date(res.atd) : null;
      res.ata = res.ata ? new Date(res.ata) : null;
      this.selectedDriver = this.drivers.find((el: Driver) => { return (el.name == res.driverName) && (el.contactNumber == res.driverContact) && (el.referenceNumber == res.referenceNumber) })


      this.domesticShipment = res;
        
      if(this.domesticShipment.totalShipments!=this.domesticShipment.numberOfShipments){
        this.domesticShipment.totalShipments=this.domesticShipment.numberOfShipments;
      }

      this.onTallyStatus(res.status!)

    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }
  calculateOveragesAndShortages() {
    if(this.domesticShipment.status== "Tally" || this.domesticShipment.status=="Arrived"){
    if(this.domesticShipment.received==null||this.domesticShipment.received==undefined){}
    else if(this.domesticShipment.received!=null||this.domesticShipment.received!=undefined){
    if(this.domesticShipment.received!>this.domesticShipment.totalShipments!){
      
      this.domesticShipment.overages=this.domesticShipment.received!-this.domesticShipment.totalShipments!
      this.domesticShipment.shortages=0
      this.domesticShipment.shortagesAwbs='';
      this.makePatternOfOverageAWBS(this.domesticShipment.overages!);
      this.makePatternOfShortageAWBS(this.domesticShipment.shortages!);
    }
    else if(this.domesticShipment.received!<this.domesticShipment.totalShipments!){
    this.domesticShipment.overages=0
    this.domesticShipment.overagesAwbs='';
    this.domesticShipment.shortages=this.domesticShipment.totalShipments!-this.domesticShipment.received!;
    this.makePatternOfOverageAWBS(this.domesticShipment.overages!);
    this.makePatternOfShortageAWBS(this.domesticShipment.shortages!);
    }
    else if(this.domesticShipment.received! === this.domesticShipment.totalShipments!){
      
      this.domesticShipment.overages=0
      this.domesticShipment.shortages=0
      this.domesticShipment.overagesAwbs='';
      this.domesticShipment.shortagesAwbs='';
    }

    }
  }
  }

  driverData() {
    this.domesticShipment.driverName = this.selectedDriver?.name;
    this.domesticShipment.driverContact = this.selectedDriver?.contactNumber;
  }

  updateDomesticShipment(domesticShipment: DomesticShipment) {
  

    let orgLocationId= this.location ?.find((el)=>{return el.country?.name == this.domesticShipment.originCountry && el.facility?.name==this.domesticShipment.originFacility && el.locationName==this.domesticShipment.originLocation && el.type == "Domestic"})!.id;
    let desLocationId= this.location ?.find((el)=>{return el.country?.name == this.domesticShipment.destinationCountry && el.facility?.name==this.domesticShipment.destinationFacility && el.locationName==this.domesticShipment.destinationLocation && el.type == "Domestic"})!.id;

    this.domesticShipmentService.updateDomesticShipment(this.domesticShipmentId,orgLocationId!,desLocationId!,domesticShipment).subscribe((res: DomesticShipment) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Outbound Updated Successfully' });

      setTimeout(() => {
        this.router.navigate(['/domestic-summary']);
      }, 800);
    }, (error: any) => {
      debugger
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.error });
      }
      // this.domesticShipment.etd =  this.domesticShipment.etd ? new Date( this.domesticShipment.etd) : null;
      // this.domesticShipment.eta =  this.domesticShipment.eta ? new Date( this.domesticShipment.eta) : null;
      this.domesticShipment.atd =  this.domesticShipment.atd ? new Date( this.domesticShipment.atd) : null;
      this.domesticShipment.ata =  this.domesticShipment.ata ? new Date( this.domesticShipment.ata) : null;
    })
  }

  getLoggedInUser() {
    this.userService.getLoggedInUser().subscribe(
      (res: User) => {
        this.user = res;
      },
      (error) => {
        if (error.error.body) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.body,
          });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error,
          });
        }
      }
    );
  }

  onSubmit() {
    // this.domesticShipment.etd = this.datePipe.transform(this.domesticShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    // this.domesticShipment.eta = this.datePipe.transform(this.domesticShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.atd = this.datePipe.transform(this.domesticShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.domesticShipment.ata = this.datePipe.transform(this.domesticShipment.ata, 'yyyy-MM-ddTHH:mm:ss')
    
    this.updateDomesticShipment(this.domesticShipment);
  }
  

  pattern1!:string;
  makePatternOfDamageAWBS(num:number|null){
  
   if(num==0 || num==null){
    this.domesticShipment.damageAwbs=''
   }

    if (num === null || num < 1) {
      this.pattern1='';
      this.cdr.detectChanges();
    }else{

      let groupPattern='';
      let separator = ','; 
      for (let index = 0; index < num; index++) {
        groupPattern += separator + '\\d{12}';
      }
      this.pattern1 = groupPattern.substring(1);
     
  this.cdr.detectChanges();
    }
  }

  pattern2!:string;
  makePatternOfOverageAWBS(num:number|null){
    debugger
    if (num === null || num < 1) {
      this.pattern2='';
      this.cdr.detectChanges();
    }else{

      let groupPattern='';
      let separator = ','; 
      for (let index = 0; index < num; index++) {
        groupPattern += separator + '\\d{12}';
      }
      this.pattern2 = groupPattern.substring(1);
     
  this.cdr.detectChanges();
    }
  }

  pattern3!:string;
  makePatternOfShortageAWBS(num:number|null){
    debugger
    if (num === null || num < 1) {
      this.pattern3='';
      this.cdr.detectChanges();
    }else{

      let groupPattern='';
      let separator = ','; 
      for (let index = 0; index < num; index++) {
        groupPattern += separator + '\\d{12}';
      }
      this.pattern3 = groupPattern.substring(1);
     
  this.cdr.detectChanges();
    }
  }

  onTallyStatus(Status:string){ 
    if(Status == "Tally"){
    this.required=true;
    }else if(Status != "Tally"){
     this.required=false;
    }
    this.cdr.detectChanges();
   }
 
}
interface originFacility {
  originFacility: string
}
