import { Component } from '@angular/core';
import { FacilityService } from '../service/facility.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Facility } from 'src/app/model/Facility';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-facility-list',
  templateUrl: './facility-list.component.html',
  styleUrls: ['./facility-list.component.scss'],
  providers:[MessageService]
})
export class FacilityListComponent {
  vTID!: number;
  visible: boolean=false;
  constructor(private facilityService:FacilityService,private messageService:MessageService,private authguardService:AuthguardService) { }
  facility!:Facility[];
  items: MenuItem[] | undefined;

  ngOnInit() {
      this.items = [{ label: 'Facility'}];
      this.getAllFacility();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllFacility(){
    this.facility=[]
    this.facilityService.getAllFacility().subscribe((res:Facility[])=>{
      this.facility=res;  
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   showDialog(id:number) {
    this.vTID=id;
    this.visible = true;
   }

   deleteVCountryByID(id:number){
    this.facilityService.deleteFacility(id).subscribe((res:any)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail:res.message});
      this.getAllFacility();
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

}
