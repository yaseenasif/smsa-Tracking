import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from '../service/location.service';
import { Router } from '@angular/router';
import { Location } from '../../../model/Location'
import { NonNullAssert } from '@angular/compiler';

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.scss'],
  providers:[MessageService]
})
export class AddLocationComponent implements OnInit {

  items: MenuItem[] | undefined;
  location:Location={
    id: null,
    locationName: null,
    status: null,
    type: null,
    originEmailsList: [{
      id: null,
      originEmail:null
    }],
    destinationEmailsList: [{
      id: null,
      destinationEmail:null
    }],
    escalationEmailsList: [{
      id: null,
      escalationEmail:null,
      level:1
    },{
      id: null,
      escalationEmail:null,
      level:2
    },{
      id: null,
      escalationEmail:null,
      level:3
    }],
  }

  type:any[]=["Domestic","International"];
  
  
  constructor(private LocationService:LocationService,
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
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Add Location'}];
  }

  onSubmit() {
    this.LocationService.addLocation(this.location).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
      setTimeout(() => {
        this.router.navigate(['/location']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Location is not added' });
    })  
  }
  

}
