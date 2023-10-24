import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from '../service/location.service';
import { Router } from '@angular/router';
import { Location } from '../../../model/Location'

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
    status: null
  }
  
  constructor(private LocationService:LocationService,
              private messageService: MessageService,
              private router: Router) { }
 
  
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
