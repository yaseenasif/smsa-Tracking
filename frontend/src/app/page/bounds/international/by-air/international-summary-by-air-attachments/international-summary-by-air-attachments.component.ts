import { HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-international-summary-by-air-attachments',
  templateUrl: './international-summary-by-air-attachments.component.html',
  styleUrls: ['./international-summary-by-air-attachments.component.scss'],
  providers:[MessageService]
})
export class InternationalSummaryByAirAttachmentsComponent {
  items: MenuItem[] | undefined;

  constructor(
    private messageService: MessageService,
    private route:ActivatedRoute,
    private internationalShippingService:InternationalShippingService
  ) { }
  
  importBayanLV:any[]=[]
  importBayanHV:any[]=[]
  otherAttachments:any[]=[]
  size=environment.fileSize;
  url=environment.baseurl.concat('/add-international-attachments/',this.route.snapshot.paramMap.get('id')!)
  uploadedFiles!:any[];

  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
  });

  onUpload(event:FileUploadEvent,name:string){
   this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: name.concat(' file is uploaded')});
   this.fileMetaDataOfIS();
  }
 
  onError(event:FileUploadErrorEvent){
    this.messageService.add({severity: 'error', summary: 'File Uploaded', detail: event.error?.error.body});
  }
  fileMetaDataOfIS(){
  
    this.importBayanLV=[]
    this.importBayanHV=[]
    this.otherAttachments=[]
  
    this.internationalShippingService.getFileMetaDataByInternationalShipment(Number( this.route.snapshot.paramMap.get('id')!)).subscribe((res)=>{
        res.forEach((element:any) => {
          if(element.attachmentType == "Import Bayan LV"){
            this.importBayanLV.push(element)
          }else if(element.attachmentType == "Import Bayan HV"){
            this.importBayanHV.push(element)
          }else if(element.attachmentType == "Other Attachments"){
            this.otherAttachments.push(element)
          }
          console.log(this.importBayanLV);
          
        });
     },(error)=>{
      this.messageService.add({severity: 'error', summary: error.error.body});
     })
  }
  deleteAttachment(id:number){
    this.internationalShippingService.deleteAttachment(id).subscribe((res)=>{
      this.fileMetaDataOfIS();
      this.messageService.add({severity: 'success', summary: 'Success', detail:"File has been deleted successfully."});
      
    },(error)=>{
      this.messageService.add({severity: 'error', summary: 'Error', detail:"File could not be deleted due to some problem."});
    });
  }

  ngOnInit(): void {  
    this.items = [{ label: 'International Inbound By Air',routerLink:'/international-summary-by-air'},{ label: 'Add Attachments'}];
    this.fileMetaDataOfIS();
  }
}
