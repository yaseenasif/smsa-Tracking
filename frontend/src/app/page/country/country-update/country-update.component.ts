import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { Country } from 'src/app/model/Country';
import { CountryService } from '../service/country.service';

@Component({
  selector: 'app-country-update',
  templateUrl: './country-update.component.html',
  styleUrls: ['./country-update.component.scss'],
  providers:[MessageService]
})
export class CountryUpdateComponent {

  items: MenuItem[] | undefined;
  vTID!: number;
  country:Country={
    id: null,
    name: null,
    status: null
  }

  constructor(private route: ActivatedRoute,
    private countryService:CountryService,
    private messageService: MessageService,
    private router: Router) { }

  name!:string;
  
  ngOnInit(): void {
    this.vTID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Country',routerLink:'/country-list'},{ label: 'Edit country'}];
    this.getCountryById();
  }

  getCountryById(){
    this.countryService.getCountryById(this.vTID).subscribe((res:Country)=>{
     this.country.name=res.name;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   onSubmit() {
    this.countryService.updateCountry(this.vTID,this.country).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Country is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/country-list']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
}

