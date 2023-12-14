import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DriverService } from '../service/driver.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Driver } from 'src/app/model/Driver';

@Component({
  selector: 'app-update-driver',
  templateUrl: './update-driver.component.html',
  styleUrls: ['./update-driver.component.scss'],
  providers:[MessageService]
})
export class UpdateDriverComponent implements OnInit {

  items: MenuItem[] | undefined;
  driver:Driver={
    id: null,
    contactNumber: null,
    name: null,
    referenceNumber: null,
    status: null
  }
  dId!:number;
 
  constructor(private route: ActivatedRoute,
    private driverService:DriverService,
    private messageService: MessageService,
    private router: Router) { }

  ngOnInit(): void {
    this.dId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Driver List',routerLink:'/driver'},{ label: 'Edit Driver'}];
    this.getDriverById();
  }

  getDriverById(){
    this.driverService.getDriverByID(this.dId).subscribe((res:Driver)=>{
     this.driver=res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   onSubmit() {
    this.driverService.updateDriverById(this.dId,this.driver).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Driver is updated on id '+res.id});
      setTimeout(() => {
        this.router.navigate(['/driver']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }

}
