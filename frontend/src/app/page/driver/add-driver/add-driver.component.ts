import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Driver } from 'src/app/model/Driver';
import { DriverService } from '../driver.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-driver',
  templateUrl: './add-driver.component.html',
  styleUrls: ['./add-driver.component.scss'],
  providers:[MessageService]
})
export class AddDriverComponent implements OnInit {
  items: MenuItem[] | undefined;
  driver:Driver={
    id: null,
    contactNumber: null,
    name: null,
    referenceNumber: null,
    status: null
  }
  constructor(private driverService:DriverService,private messageService:MessageService,private router:Router) { }
  name!:string;
  contactNumber!:string;
  referenceNumber!:string;
  ngOnInit(): void {
    this.items = [{ label: 'Driver List',routerLink:'/driver'},{ label: 'Add Driver'}];
  }

  onSubmit() {
    this.driverService.addDriver(this.driver).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Driver is added' });
      setTimeout(() => {
        this.router.navigate(['/driver']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
  

}
