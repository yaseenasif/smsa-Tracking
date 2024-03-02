import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { Email } from 'src/app/model/EmailAddressForRoutes';
import { DomesticRoutesService } from 'src/app/page/domesticRoutes/service/domestic-routes.service';

@Component({
  selector: 'app-add-international-by-road-email-list',
  templateUrl: './add-international-by-road-email-list.component.html',
  styleUrls: ['./add-international-by-road-email-list.component.scss'],
  providers:[MessageService]
})
export class AddInternationalByRoadEmailListComponent {
  items: MenuItem[] | undefined;

  emailAddressForRoutes:Email={
    id: null,
    emails: null,
    type: 'International Road'
  }

  constructor(private domesticRoutesService:DomesticRoutesService,
              private messageService:MessageService,
              private router:Router){}

  ngOnInit(){
    this.items = [{ label: 'International Route List For Road',routerLink:'/international-routes-for-road' },{ label: 'Update Emails' }];
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
    this.domesticRoutesService.updateEmail(3, this.emailAddressForRoutes).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: "Emails has been updated" });
      setTimeout(() => {
        this.router.navigate(['/international-routes-for-road']);
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
