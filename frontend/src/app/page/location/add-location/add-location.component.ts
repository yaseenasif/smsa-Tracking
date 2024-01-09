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

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.scss'],
  providers:[MessageService]
})
export class AddLocationComponent implements OnInit {

  items: MenuItem[] | undefined;
  location:Location={
    id: undefined,
    locationName: undefined,
    type: undefined,
    originEmail: null,
    destinationEmail: null,
    status: undefined,
    originEscalation: [],
    destinationEscalation: [],
    facility:undefined
  }
  country!:Country[];
  countryName!:any;
  facility!:Facility[];

  

  type:any[]=["Domestic","International"];
  
  
  constructor(private LocationService:LocationService,
              private messageService: MessageService,
              private router: Router,
              private countryService:CountryService,
              private facilityService:FacilityService) { }

 
  
  ngOnInit(): void {
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Add Location'}];
    this.getAllCountry();
  }

  getCountryBySelectedFacility(){
    this.getFacilityByCountryId(this.countryName.id);
  }
  
  getAllCountry(){
    this.countryService.getAllCountry().subscribe((res:Country[])=>{
      this.country=res;  
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   getFacilityByCountryId(id:number){
    this.facilityService.getFacilityByCountryID(id).subscribe((res:Facility[])=>{
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
