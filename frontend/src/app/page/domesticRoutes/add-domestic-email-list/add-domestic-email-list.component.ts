import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Email } from 'src/app/model/EmailAddressForRoutes';
import { DomesticRoutesService } from '../service/domestic-routes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-domestic-email-list',
  templateUrl: './add-domestic-email-list.component.html',
  styleUrls: ['./add-domestic-email-list.component.scss'],
  providers:[MessageService],
})
export class AddDomesticEmailListComponent {

  items: MenuItem[] | undefined;

  emailAddressForRoutes:Email={
    id: null,
    emails: null,
    type: 'Domestic'
  }

  constructor(private domesticRoutesService:DomesticRoutesService,
              private messageService:MessageService,
              private router:Router){}

  ngOnInit(){
    this.items = [{ label: 'Domestic Route List',routerLink:'/domestic-routes' },{ label: 'Update Emails' }];
    this.getEmail()
  }
  getEmail(){
    this.domesticRoutesService.getEmail(this.emailAddressForRoutes.type!).subscribe(res=>{
      if(typeof  res.emails == 'string'){
      res.emails=res.emails!.split(',')
      }
      this.emailAddressForRoutes=res
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }


  onSubmit() {
    if(Array.isArray(this.emailAddressForRoutes.emails)){
    this.emailAddressForRoutes.emails=this.emailAddressForRoutes.emails.join(",")
    this.domesticRoutesService.updateEmail(1, this.emailAddressForRoutes).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: "Emails has been updated" });
      setTimeout(() => {
        this.router.navigate(['/domestic-routes']);
      }, 800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
    
    }
     if(typeof this.emailAddressForRoutes.emails == 'string'){
    this.emailAddressForRoutes.emails=this.emailAddressForRoutes.emails?.split(",")
    console.log(this.emailAddressForRoutes.emails);
    }
  }
}
