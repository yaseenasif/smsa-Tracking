import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from '../service/location.service';
import { Router } from '@angular/router';
import { Location } from '../../../model/Location'
import { NonNullAssert } from '@angular/compiler';
import { CountryService } from '../../country/service/country.service';
import { Country } from 'src/app/model/Country';
import { FacilityService } from '../../facility/service/facility.service';
import { Facility } from 'src/app/model/Facility';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';
import { Observable, catchError, forkJoin } from 'rxjs';


@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.scss'],
  providers:[MessageService]
})
export class AddLocationComponent implements OnInit {

  items: MenuItem[] | undefined;
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
  country!:Country[];
  countryName!:any;
  facility!:Facility[];



  type:ProductField | null | undefined;


  constructor(private LocationService:LocationService,
              private messageService: MessageService,
              private router: Router,
              private countryService:CountryService,
              private facilityService:FacilityService,
              private productFieldService:ProductFieldServiceService) { }



  ngOnInit(): void {
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Add Location'}];
    this.getAllCountry();
    this.getAllFacility();
    // this.getAllLocationType();

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

  onSubmit() {
    if(Array.isArray(this.location.originEmail) && Array.isArray(this.location.originEscalationLevel1)&& Array.isArray(this.location.originEscalationLevel2)&& Array.isArray(this.location.originEscalationLevel3)&& Array.isArray(this.location.destinationEmail)&& Array.isArray(this.location.destinationEscalationLevel1)&& Array.isArray(this.location.destinationEscalationLevel2)&& Array.isArray(this.location.destinationEscalationLevel3)){
      this.location.originEmail=this.location.originEmail!.join(',');
      this.location.destinationEmail=this.location.destinationEmail!.join(',');
      this.location.originEscalationLevel1=this.location.originEscalationLevel1!.join(',');
      this.location.originEscalationLevel2=this.location.originEscalationLevel2!.join(',');
      this.location.originEscalationLevel3=this.location.originEscalationLevel3!.join(',');
      this.location.destinationEscalationLevel1=this.location.destinationEscalationLevel1!.join(',');
      this.location.destinationEscalationLevel2=this.location.destinationEscalationLevel2!.join(',');
      this.location.destinationEscalationLevel3=this.location.destinationEscalationLevel3!.join(',');
    }

    this.LocationService.addLocation(this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
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
