import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';
import { InternationalShippingService } from '../../international/service/international-shipping.service';



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
    private internationalShippingService: InternationalShippingService
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
  
  ngOnInit(): void {  
    

    this.items = [{ label: 'Domestic Outbound',routerLink:'/domestic-shipping'},{ label: 'Add Attachments'}];
    this.fileMetaDataOfDS();
  }

 
}






