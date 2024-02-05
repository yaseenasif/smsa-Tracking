import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Country } from 'src/app/model/Country';
import { Facility } from 'src/app/model/Facility';
import { FacilityService } from '../service/facility.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CountryService } from '../../country/service/country.service';

@Component({
  selector: 'app-facility-update',
  templateUrl: './facility-update.component.html',
  styleUrls: ['./facility-update.component.scss'],
  providers:[MessageService]
})
export class FacilityUpdateComponent {
  vTID!: number;
  items: MenuItem[] | undefined;
  country!:Country[];
  facility:Facility={
    id: undefined,
    name: undefined,
    status: undefined
  };

  constructor(private facilityService:FacilityService,
              private messageService: MessageService,
              private router: Router,
              private countryService: CountryService,
              private route: ActivatedRoute) { }
 
  
  ngOnInit(): void {
    this.vTID = +this.route.snapshot.paramMap.get('id')!;
    // this.getAllCountry();
    this.items = [{ label: 'Facility',routerLink:'/facility-list'},{ label: 'Add facility'}];
    this.getFacilityById(this.vTID);

  }

  // getAllCountry(){
  //   this.countryService.getAllCountry().subscribe((res:Country[])=>{
  //     this.country=res;  
  //     console.log(this.country);
      
  //   },error=>{
  //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  //   })
  //  }

   getFacilityById(id:number){
    
      this.facilityService.getFacilityById(this.vTID).subscribe((res:Facility)=>{
        console.log("Facility");
        console.log(res);
        
        
        // this.facility.id=res.id;
        // this.facility.name=res.name;
        // this.facility.country=res.country;
        this.facility=res
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
