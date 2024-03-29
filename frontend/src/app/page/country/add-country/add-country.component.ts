import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Country } from 'src/app/model/Country';
import { CountryService } from '../service/country.service';
import { Router } from '@angular/router';
import { Facility } from 'src/app/model/Facility';

@Component({
  selector: 'app-add-country',
  templateUrl: './add-country.component.html',
  styleUrls: ['./add-country.component.scss'],
  providers:[MessageService]
})
export class AddCountryComponent {

  items: MenuItem[] | undefined;
  country:Country={
    id: null,
    name: null,
    status: null
  };

  // facility:Facility[]=[{
  //   id: 1,
  //   name: "HUB",
  //   country: undefined
  // },{
  //   id: 2,
  //   name: "STATION",
  //   country: undefined
  // },{
  //   id: 3,
  //   name: "GATEWAY",
  //   country: undefined
  // }]

  constructor(private countryService:CountryService,
              private messageService: MessageService,
              private router: Router) { }
 
  
  ngOnInit(): void {
    this.items = [{ label: 'Country',routerLink:'/country-list'},{ label: 'Add Country'}];
  }

  onSubmit() {
    this.countryService.addCountry(this.country).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Country is added' });
      setTimeout(() => {
        this.router.navigate(['/country-list']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
}
