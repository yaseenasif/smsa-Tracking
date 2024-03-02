import { ChangeDetectorRef, Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
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
import { Location } from 'src/app/model/Location'
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { Observable, catchError, forkJoin } from 'rxjs';
import { DatePipe } from '@angular/common';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/page/user/service/user.service';

@Component({
  selector: 'app-update-international-road-for-summary',
  templateUrl: './update-international-road-for-summary.component.html',
  styleUrls: ['./update-international-road-for-summary.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateInternationalRoadForSummaryComponent {
  items: MenuItem[] | undefined;
  defaultDate:Date=new Date(this.datePipe.transform((new Date()).setHours(0, 0, 0, 0),'EEE MMM dd yyyy HH:mm:ss \'GMT\'ZZ (z)')!)
  iSID!: number;
  required:boolean=false;
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
    numberOfBoxes: undefined,
    damage: null,
    damageAwbs: null,
    numberOfPalletsReceived: null,
    numberOfBagsReceived: null
  }
  minDate: Date = new Date();
  location!: Location[];
  // locationPort!: LocationPort[]
  drivers!: Driver[]
  vehicleTypes!: VehicleType[]
  shipmentStatus!: ProductField;
  selectedDriver!: Driver | null | undefined;
  modeOptions: { options: string }[] = Object.values(Mode).map(el => ({ options: el }));
  shipmentMode: { options: string }[] = Object.values(ShipmentMode).map(el => ({ options: el }));
  numberOfPallets: { options: number }[] = Object.values(NumberOfPallets).filter(value => typeof value === 'number').map(value => ({ options: value as number }));

  selectedLocation!: Location;
  user!: User;
 

  constructor(private router: Router,
    private cdr: ChangeDetectorRef,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private locationService: LocationService,
    private locationPortService: LocationPortService,
    private driverService: DriverService,
    private route: ActivatedRoute,
    private vehicleTypeService: VehicleTypeService,
    private shipmentStatusService: ProductFieldServiceService,
    private userService:UserService,
    private datePipe: DatePipe) { }

  name!: string;
  checked!: boolean;
  size = 100000
  uploadedFiles: any[] = [];
 
  onPasteOveragesAwbs() {  
    this.internationalShipment.overageAWBs=this.internationalShipment.overageAWBs!.match(/[^ ,]+/g)!.join(',')
  }
  onPasteShortagesAwbs() {  
    this.internationalShipment.shortageAWBs=this.internationalShipment.shortageAWBs!.match(/[^ ,]+/g)!.join(',')
  }
  onPasteDamageAwbs() {  
    this.internationalShipment.damageAwbs=this.internationalShipment.damageAwbs!.match(/[^ ,]+/g)!.join(',')
  }
   

  onUpload1(event: any) {
    for (let file of event.files) {
      this.uploadedFiles.push(file);
    }
  }


  ngOnInit(): void {
    this.getLoggedInUser();
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Inbound By Road', routerLink: '/international-summary-by-road' }, { label: 'Edit International Inbound By Road' }];

    const locations$: Observable<Location[]> = this.locationService.getAllLocation();
    // const locationPort$: Observable<LocationPort[]> = this.locationPortService.getAllLocationPort();
    const driver$: Observable<PaginatedResponse<Driver>> = this.driverService.getAllDriver();
    const vehicleType$: Observable<VehicleType[]> = this.vehicleTypeService.getALLVehicleType();
    const shipmentStatus$: Observable<ProductField> = this.getAllShipmentStatus();

    forkJoin([locations$,  driver$, vehicleType$, shipmentStatus$]).subscribe(
      ([locationsResponse, driverResponse, vehicleTypeResponse, shipmentStatusResponse]) => {
        // Access responses here
        this.location = locationsResponse.filter(el => el.status);
        // this.locationPort = locationPortResponse.filter(el => el.status);
        this.drivers = driverResponse.content.filter((el: Driver) => el.status);
        this.vehicleTypes = vehicleTypeResponse
        this.shipmentStatus = shipmentStatusResponse

        // Now that you have the responses, you can proceed with the next steps
        this.getInternationalShipmentById(this.iSID);
      }
    );
  }

  onSubmit() {
    let orgLocationId=this.user.internationalRoadOriginLocation?.find((el)=>{return el.country?.name == this.internationalShipment.originCountry && el.facility?.name==this.internationalShipment.originFacility && el.locationName==this.internationalShipment.originLocation})!.id;
    let desLocationId=this.user.internationalRoadDestinationLocation?.find((el)=>{return el.country?.name == this.internationalShipment.destinationCountry && el.facility?.name==this.internationalShipment.destinationFacility && el.locationName==this.internationalShipment.destinationLocation})!.id;
    this.internationalShipment.etd = this.datePipe.transform(this.internationalShipment.etd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.eta = this.datePipe.transform(this.internationalShipment.eta, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.atd = this.datePipe.transform(this.internationalShipment.atd, 'yyyy-MM-ddTHH:mm:ss')
    this.internationalShipment.ata = this.datePipe.transform(this.internationalShipment.ata, 'yyyy-MM-ddTHH:mm:ss')

    this.internationalShippingService.updateInternationalShipmentById(this.iSID, this.internationalShipment,orgLocationId!,desLocationId!).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Shipment is updated on id' + res.id });
      setTimeout(() => {
        this.router.navigate(['/international-summary-by-road']);
      }, 800);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      this.internationalShipment.ata =  this.internationalShipment.ata ? new Date( this.internationalShipment.ata) : null;
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

  getInternationalShipmentById(id: number) {

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res: InternationalShipment) => {
      res.etd = res.etd ? new Date(res.etd) : null;
      res.eta = res.eta ? new Date(res.eta) : null;
      res.atd = res.atd ? new Date(res.atd) : null;
      res.ata = res.ata ? new Date(res.ata) : null;
      this.selectedDriver = this.drivers.find(el => (el.name == res.driverName) && (el.contactNumber == res.driverContact) && (el.referenceNumber == res.referenceNumber))

      this.internationalShipment = res;
      if(this.internationalShipment.totalShipments!=this.internationalShipment.numberOfShipments){
        this.internationalShipment.totalShipments=this.internationalShipment.numberOfShipments;
      }
      this.onTallyStatus(res.status!)

    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllLocations() {
    this.locationService.getAllLocation().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);


    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllLocationPort() {
    // this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
    //   this.locationPort = res.filter(el => el.status)
    // }, error => {
    //   this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    // })
  }
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
    return this.shipmentStatusService.getProductFieldByName("Destination_Of_International_By_Road").pipe(
      catchError(error => {
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

  
  calculateOveragesAndShortages() {
    if(this.internationalShipment.received==null||this.internationalShipment.received==undefined){}
    else if(this.internationalShipment.received!=null||this.internationalShipment.received!=undefined){
    if(this.internationalShipment.received!>this.internationalShipment.totalShipments!){
      this.internationalShipment.overages=this.internationalShipment.received!-this.internationalShipment.totalShipments!
      this.internationalShipment.shortages=0
      this.makePatternOfOverageAWBS(this.internationalShipment.overages!);
      this.makePatternOfShortageAWBS(this.internationalShipment.shortages!);
    }
    else if(this.internationalShipment.received!<this.internationalShipment.totalShipments!){
    this.internationalShipment.overages=0
    this.internationalShipment.shortages=this.internationalShipment.totalShipments!-this.internationalShipment.received!;
    this.makePatternOfOverageAWBS(this.internationalShipment.overages!);
    this.makePatternOfShortageAWBS(this.internationalShipment.shortages!);
    }
    else if(this.internationalShipment.received! === this.internationalShipment.totalShipments!){
      this.internationalShipment.overages=0
      this.internationalShipment.shortages=0
    }

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
  

   pattern1!:string;
   makePatternOfDamageAWBS(num:number|null){
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
}




