import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from '../service/location.service';
import { Location } from '../../../model/Location'
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.scss'],
  providers:[MessageService]
})
export class LocationListComponent implements OnInit {


  constructor(private locationService:LocationService,private messageService:MessageService,private authguardService:AuthguardService) { }
 
  items: MenuItem[] | undefined;
  location!:Location[];
  visible: boolean = false;
  lID!:number

  ngOnInit() {
      this.items = [{ label: 'Location List'}];
      this.getAllLocations();
  }
  
  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.location=res.filter(el => el.status);   
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteLocationByID(id:number){
    this.locationService.deleteLocationByID(id).subscribe((res:Location)=>{
      this.getAllLocations();
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is deleted on id '+res!.id!.toString()});
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

  showDialog(id:number) {
    this.lID=id;
    this.visible = true;
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

}
