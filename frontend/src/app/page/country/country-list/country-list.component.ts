import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { CountryService } from '../service/country.service';
import { Country } from 'src/app/model/Country';
import { VehicleType } from 'src/app/model/VehicleType';

@Component({
  selector: 'app-country-list',
  templateUrl: './country-list.component.html',
  styleUrls: ['./country-list.component.scss'],
  providers:[MessageService]
})
export class CountryListComponent {
  vTID!: number;
  visible: boolean=false;
  constructor(private countryService:CountryService,private messageService:MessageService) { }
  country!:Country[];
  items: MenuItem[] | undefined;

  ngOnInit() {
      this.items = [{ label: 'Country'}];
      this.getAllCountry();
  }

  getAllCountry(){
    this.countryService.getAllCountry().subscribe((res:Country[])=>{
      this.country=res;  
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   showDialog(id:number) {
    this.vTID=id;
    this.visible = true;
   }

   deleteVCountryByID(id:number){
    this.countryService.deleteCountry(id).subscribe((res:any)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail:res.message});
      this.getAllCountry();
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

}
