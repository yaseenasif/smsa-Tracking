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
    originEscalation: [],
    destinationEscalation: [],
    status: null,
    facility: null,
    country: null
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
    if(Array.isArray(this.location.originEmail) && Array.isArray(this.location.originEscalation)&& Array.isArray(this.location.destinationEmail)&& Array.isArray(this.location.destinationEscalation)){
      this.location.originEmail=this.location.originEmail!.join(',');
      this.location.destinationEmail=this.location.destinationEmail!.join(',');
      this.location.originEscalation=this.location.originEscalation!.join(',');
      this.location.destinationEscalation=this.location.destinationEscalation!.join(',');
    }

    this.LocationService.addLocation(this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
      setTimeout(() => {
        this.router.navigate(['/location']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }


}
