import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Facility } from 'src/app/model/Facility';
import { FacilityService } from '../service/facility.service';
import { Router } from '@angular/router';
import { CountryService } from '../../country/service/country.service';
import { Country } from 'src/app/model/Country';

@Component({
  selector: 'app-add-facility',
  templateUrl: './add-facility.component.html',
  styleUrls: ['./add-facility.component.scss'],
  providers:[MessageService]
})
export class AddFacilityComponent {

  items: MenuItem[] | undefined;
  country!:Country[];
  facility:Facility={
    id: null,
    name: null,
    country:null,
  };

  constructor(private facilityService:FacilityService,
              private messageService: MessageService,
              private router: Router,
              private countryService: CountryService) { }
 
  
  ngOnInit(): void {
    this.items = [{ label: 'Facility',routerLink:'/facility-list'},{ label: 'Add facility'}];
    this.getAllCountry();
  }

  getAllCountry(){
    this.countryService.getAllCountry().subscribe((res:Country[])=>{
      this.country=res;  
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

  onSubmit() {
    this.facilityService.addFacility(this.facility).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'facility is added' });
      setTimeout(() => {
        this.router.navigate(['/facility-list']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
}
