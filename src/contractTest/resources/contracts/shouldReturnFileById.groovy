package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpHeaders

Contract.make {
    description 'should return all courses'
    request {
        method GET()
        url(regex('/api/journal/files/([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})')) {
            headers {
                header('Authorization', execute('authToken()'))
            }
        }
    }
    response {
        headers {
            header(HttpHeaders.CONTENT_TYPE, matching('application/pdf'))

        }
        status 200
    }
}
