import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';
import { InternationalShippingService } from '../../international/service/international-shipping.service';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';



@Component({
  selector: 'app-domestic-attachments',
  templateUrl: './domestic-attachments.component.html',
  styleUrls: ['./domestic-attachments.component.scss'],
  providers:[MessageService]
})
export class DomesticAttachmentsComponent {
  items: MenuItem[] | undefined;

  constructor(
    private messageService: MessageService,
    private route:ActivatedRoute,
    private internationalShippingService: InternationalShippingService,
    private authguardService:AuthguardService
  ) { }
  
  size=environment.fileSize;
  url=environment.baseurl.concat('/add-attachments/',this.route.snapshot.paramMap.get('id')!)
  uploadedFiles!:any[];
  fileMetaData!:any[];
  vehicleManifest:any[]=[];
  otherAttachments:any[]=[];

  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
  });

  onUpload(event:FileUploadEvent,name:string){
   this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: name.concat(' file is uploaded')});
   this.fileMetaDataOfDS();
  }
 

  onError(event:FileUploadErrorEvent){
    this.messageService.add({severity: 'error', summary: 'File Uploaded', detail: event.error?.error.body});
  }

  fileMetaDataOfDS(){
    this.vehicleManifest=[]
    this.otherAttachments=[]
    this.internationalShippingService.getFileMetaDataByDomesticShipment(Number( this.route.snapshot.paramMap.get('id')!)).subscribe((res)=>{
        res.forEach((element:any) => {
          if(element.attachmentType == "Vehicle Manifest"){
            this.vehicleManifest.push(element)
          }else if(element.attachmentType == "Other Attachments"){
            this.otherAttachments.push(element)
          }
        });
     },(error)=>{
      this.messageService.add({severity: 'error', summary: error.error.body});
     })
  }
  deleteAttachment(id:number){
    this.internationalShippingService.deleteAttachment(id).subscribe((res)=>{
      this.fileMetaDataOfDS();
      this.messageService.add({severity: 'success', summary: 'Success', detail:"File has been deleted successfully."});
      
    },(error)=>{
      this.messageService.add({severity: 'error', summary: 'Error', detail:"File could not be deleted due to some problem."});
    });
  }
  ngOnInit(): void {  
    

    this.items = [{ label: 'Domestic Outbound',routerLink:'/domestic-shipping'},{ label: 'Add Attachments'}];
    this.fileMetaDataOfDS();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }
 
}






