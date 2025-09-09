import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { Location } from 'src/app/model/Location';
import { LocationService } from '../service/location.service';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';
import { CountryService } from '../../country/service/country.service';
import { FacilityService } from '../../facility/service/facility.service';
import { Observable, catchError, forkJoin, switchMap } from 'rxjs';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';

@Component({
  selector: 'app-update-location',
  templateUrl: './update-location.component.html',
  styleUrls: ['./update-location.component.scss'],
  providers:[MessageService]
})
export class UpdateLocationComponent implements OnInit {

  items: MenuItem[] | undefined;
  lID!:number
  location:Location={
    id: null,
    locationName: null,
    type: null,
    originEmail: null,
    destinationEmail: null,
    status: null,
    facility: null,
    country: null,
    originEscalationLevel1: null,
    originEscalationLevel2: null,
    originEscalationLevel3: null,
    destinationEscalationLevel1: null,
    destinationEscalationLevel2: null,
    destinationEscalationLevel3: null
  }

  type:ProductField | null | undefined;
  constructor(private route: ActivatedRoute,
    private locationService:LocationService,
    private messageService: MessageService,
    private countryService:CountryService,
    private facilityService:FacilityService,
    private productFieldService:ProductFieldServiceService,
    private router: Router) { }
    country!:Country[];
    countryName!:any;
    facility!:Facility[];

  ngOnInit(): void {
    this.lID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Edit Location'}];
    this.getAllCountry();
    this.getAllFacility();
 

    this.locationService.getLocationByID(this.lID).subscribe((res:Location)=>{
      if (typeof res.originEmail === 'string' && typeof res.destinationEmail === 'string' && typeof res.originEscalationLevel1 === 'string' && typeof res.originEscalationLevel2 === 'string' && typeof res.originEscalationLevel3 === 'string' && typeof res.destinationEscalationLevel1 === 'string' && typeof res.destinationEscalationLevel2 === 'string' && typeof res.destinationEscalationLevel3 === 'string') {
      res.originEmail=res.originEmail!.split(',')
      res.destinationEmail=res.destinationEmail!.split(',')
      res.originEscalationLevel1=res.originEscalationLevel1!.split(',')
      res.originEscalationLevel2=res.originEscalationLevel2!.split(',')
      res.originEscalationLevel3=res.originEscalationLevel3!.split(',')

      res.destinationEscalationLevel1=res.destinationEscalationLevel1!.split(',')
      res.destinationEscalationLevel2=res.destinationEscalationLevel2!.split(',')
      res.destinationEscalationLevel3=res.destinationEscalationLevel3!.split(',')

    }
    this.location=res;
    // this.facilityService.getFacilityByCountryID(res.facility?.country?.id!).subscribe((res2:Facility[])=>{
    //   this.facility=res2;
    //   this.countryName=res.facility?.country
    //   this.location=res;
    // },error=>{
    //   this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    // })

    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })

    const locationType$: Observable<ProductField> = this.getAllLocationType();

    forkJoin([locationType$]).subscribe(
      ([locationTypeResponse]) => {
        // Access responses here
        this.type = locationTypeResponse;
      }
    );


  }

  getAllLocationType(): Observable<ProductField> {
    return this.productFieldService.getProductFieldByName("Location_Type").pipe(
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

  // getCountryBySelectedFacility(){
  //   this.getFacilityByCountryId(this.countryName.id);
  // }

  getAllCountry(){
    this.countryService.getAllCountry().subscribe((res:Country[])=>{
      this.country=res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   getAllFacility(){
    this.facilityService.getAllFacility().subscribe((res:Facility[])=>{
      this.facility=res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   getFacilityByCountryId(id:number){
    this.facilityService.getFacilityByCountryID(id).subscribe((res:Facility[])=>{
      this.facility=res;
      console.log(res);

    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   getLocationByID(){
    this.locationService.getLocationByID(this.lID).subscribe((res:Location)=>{
      if (typeof res.originEmail === 'string' && typeof res.destinationEmail === 'string' && typeof res.originEscalationLevel1 === 'string' && typeof res.originEscalationLevel2 === 'string' && typeof res.originEscalationLevel3 === 'string' && typeof res.destinationEscalationLevel1 === 'string' && typeof res.destinationEscalationLevel2 === 'string' && typeof res.destinationEscalationLevel3 === 'string') {
      res.originEmail=res.originEmail!.split(',')
      res.destinationEmail=res.destinationEmail!.split(',')
      res.originEscalationLevel1=res.originEscalationLevel1!.split(',')
      res.originEscalationLevel2=res.originEscalationLevel2!.split(',')
      res.originEscalationLevel3=res.originEscalationLevel3!.split(',')

      res.destinationEscalationLevel1=res.destinationEscalationLevel1!.split(',')
      res.destinationEscalationLevel2=res.destinationEscalationLevel2!.split(',')
      res.destinationEscalationLevel3=res.destinationEscalationLevel3!.split(',')
    }

    // this.countryName=res.facility?.country
    // console.log(this.countryName);

     this.location=res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   onSubmit() {
    if(Array.isArray(this.location.originEmail) && Array.isArray(this.location.originEscalationLevel1) && Array.isArray(this.location.originEscalationLevel2) && Array.isArray(this.location.originEscalationLevel3)&& Array.isArray(this.location.destinationEmail)&& Array.isArray(this.location.destinationEscalationLevel1) && Array.isArray(this.location.destinationEscalationLevel2) && Array.isArray(this.location.destinationEscalationLevel3) ){
      this.location.originEmail=this.location.originEmail!.join(',');
      this.location.destinationEmail=this.location.destinationEmail!.join(',');
      this.location.originEscalationLevel1=this.location.originEscalationLevel1!.join(',');
      this.location.originEscalationLevel2=this.location.originEscalationLevel2!.join(',');
      this.location.originEscalationLevel3=this.location.originEscalationLevel3!.join(',');

      this.location.destinationEscalationLevel1=this.location.destinationEscalationLevel1!.join(',');
      this.location.destinationEscalationLevel2=this.location.destinationEscalationLevel2!.join(',');
      this.location.destinationEscalationLevel3=this.location.destinationEscalationLevel3!.join(',');

    }
    this.locationService.updateLocationById(this.lID,this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/location']);
      },800);
    },error=>{

   
      if (typeof  this.location.originEmail === 'string' &&
          typeof  this.location.destinationEmail === 'string' && 
          typeof  this.location.originEscalationLevel1 === 'string' && 
          typeof  this.location.originEscalationLevel2 === 'string' && 
          typeof  this.location.originEscalationLevel3 === 'string' && 
          typeof  this.location.destinationEscalationLevel1 === 'string' &&
          typeof  this.location.destinationEscalationLevel2 === 'string' && 
          typeof  this.location.destinationEscalationLevel3 === 'string') {

         this.location.originEmail= this.location.originEmail!.split(',')
         this.location.destinationEmail= this.location.destinationEmail!.split(',')
         this.location.originEscalationLevel1= this.location.originEscalationLevel1!.split(',')
         this.location.originEscalationLevel2= this.location.originEscalationLevel2!.split(',')
         this.location.originEscalationLevel3= this.location.originEscalationLevel3!.split(',')
         this.location.destinationEscalationLevel1= this.location.destinationEscalationLevel1!.split(',')
         this.location.destinationEscalationLevel2= this.location.destinationEscalationLevel2!.split(',')
         this.location.destinationEscalationLevel3= this.location.destinationEscalationLevel3!.split(',')
      }
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

}
