import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationPort } from 'src/app/model/LocationPort';
import { Location } from 'src/app/model/Location';
import { LocationService } from '../../location/service/location.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationPortService } from '../service/location-port.service';

@Component({
  selector: 'app-update-location-port',
  templateUrl: './update-location-port.component.html',
  styleUrls: ['./update-location-port.component.scss'],
  providers:[MessageService]
})
export class UpdateLocationPortComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor(private locationService:LocationService,
              private locationPortService:LocationPortService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) { }
  lpID!:number;
  locationPort: LocationPort = {
    id: null,
    location: {
      id: null,
      locationName: null,
      type: null,
      status: null
    },
    portName: null,
    status: null
  };
  
  location!:Location[];
  

  ngOnInit(): void {
    this.items = [{ label: 'Location Port List',routerLink:'/location-port'},{ label: 'Edit Location Port'}];
    this.lpID = +this.route.snapshot.paramMap.get('id')!;
     this.getAllLocation();
     this.getLocationPortByID();
  }

  getAllLocation(){
    this.locationService.getAllLocationForInternational().subscribe((res:Location[])=>{
      this.location=res.filter(location => location.status)    
    },error=>{})
  }

  getLocationPortByID(){
    this.locationPortService.getLocationPortByID(this.lpID).subscribe((res:LocationPort)=>{
      this.locationPort=res
     },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not find location port on this id'});
     })
  }
 
  onSubmit(){
    this.locationPortService.updateLocationPortById(this.lpID,this.locationPort).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location Port is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/location-port']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Location Port is not updated'});
    })  
  }

}





