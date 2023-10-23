import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationPort } from 'src/app/model/LocationPort';
import { LocationService } from '../../location/service/location.service';
import { LocationPortService } from '../service/location-port.service';
import { Location } from 'src/app/model/Location';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-location-port',
  templateUrl: './add-location-port.component.html',
  styleUrls: ['./add-location-port.component.scss'],
  providers:[MessageService]
})
export class AddLocationPortComponent implements OnInit {
  items: MenuItem[] | undefined;

  constructor(private locationService:LocationService,
              private locationPortService:LocationPortService,
              private messageService:MessageService,
              private router: Router) { }

  locationPort:LocationPort={
    id: null,
    location: null,
    portName: null,
    status: null
  };
  location!:Location[];
  name!:string;

  ngOnInit(): void {
    this.items = [{ label: 'Location Port List',routerLink:'/location-port'},{ label: 'Add Location Port'}];  
    this.getAllLocation()
  }

  getAllLocation(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.location=res.filter(location => location.status)
      console.log(this.location);
      
    },error=>{})
  }

    onSubmit() {
      console.log(this.locationPort);
      
    // this.locationPortService.addLocationPort(this.locationPort).subscribe(res=>{
    //   this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location Port is added' });
    //   setTimeout(() => {
    //     this.router.navigate(['/location-port']);
    //   },800);
    // },error=>{
    //   this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Location Port is not added' });
    // })  
  }

}



