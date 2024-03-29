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
    originEscalation: [],
    destinationEscalation: [],
    status: null,
    facility: null,
    country: null
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
      if (typeof res.originEmail === 'string' && typeof res.destinationEmail === 'string' && typeof res.originEscalation === 'string' && typeof res.destinationEscalation === 'string') {
      res.originEmail=res.originEmail!.split(',')
      res.destinationEmail=res.destinationEmail!.split(',')
      res.originEscalation=res.originEscalation!.split(',')
      res.destinationEscalation=res.destinationEscalation!.split(',')
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
      if (typeof res.originEmail === 'string' && typeof res.destinationEmail === 'string' && typeof res.originEscalation === 'string' && typeof res.destinationEscalation === 'string') {
      res.originEmail=res.originEmail!.split(',')
      res.destinationEmail=res.destinationEmail!.split(',')
      res.originEscalation=res.originEscalation!.split(',')
      res.destinationEscalation=res.destinationEscalation!.split(',')
    }

    // this.countryName=res.facility?.country
    // console.log(this.countryName);

     this.location=res;
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
    this.locationService.updateLocationById(this.lID,this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/location']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

}
