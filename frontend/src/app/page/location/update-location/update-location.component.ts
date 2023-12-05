import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { Location } from 'src/app/model/Location';
import { LocationService } from '../service/location.service';

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
    status: null,
    type: null,
    originEmailsList: null,
    destinationEmailsList: null,
    escalationEmailsList: null
  }

  type:any[]=["Domestic","International"];
  constructor(private route: ActivatedRoute,
    private locationService:LocationService,
    private messageService: MessageService,
    private router: Router) { }

    deleteOrigenEmail(index:number){
      this.location.originEmailsList!.splice(index,1);
    }
    addOrigenEmail(){
    this.location.originEmailsList!.push({
    id:null,
    originEmail:null
    })
    }      
    deleteDestinationEmail(index:number){
      this.location.destinationEmailsList!.splice(index,1);
    }
    addDestinationEmail(){
    this.location.destinationEmailsList!.push({
    id:null,
    destinationEmail:null
    })
    }      
 
  
  ngOnInit(): void {
    this.lID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Edit Location'}];
    this.getPermissionById();
  }
  
  getPermissionById(){
    this.locationService.getLocationByID(this.lID).subscribe((res:Location)=>{
     this.location=res;
    },error=>{
     this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not find location on this id'});
    })
   }

   onSubmit() {
    this.locationService.updateLocationById(this.lID,this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/location']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Location is not updated'});
    })  
  }

}
